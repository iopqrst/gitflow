package com.bskcare.ch.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.bskcare.ch.util.DateUtils;
import com.bskcare.ch.vo.Electrocardiogram;


public class ReadFromFileDrawImage {
    /**
     * 以字节为单位读取文件内容，一次读一个字节
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     * 从系统中拿到scp文件，按照字节流读取文件，解析出 测试时间，平均心跳  等等...
     *  然后在相应images/electrocardiogram路径下生成png图片!	
     */
    public static Electrocardiogram readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        
        try {
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
                     
            int count = 1 ;
            int[] ecgData = new int[9000] ;
            int ecgDatacount = 0 ;
            
            int[] dateTime = new int[7] ;
            int dateTimeCount = 0 ;
            //心电图数据
            Electrocardiogram electrocardiogram = new Electrocardiogram() ;
                       	
            while ((tempbyte = in.read()) != -1){
            	
            	//从低197个数据开始取ECG数据
            	if(count<=196){
            		//取出日期
            		if(count>=102&&count<=105||count>=109&&count<=111){
            			dateTime[dateTimeCount] = tempbyte ;
            			dateTimeCount ++ ;
            		}
            		//ECG数据                	
            	}else if(count>=197&&count<=9196){            		
            		ecgData[ecgDatacount] = tempbyte ;
            		ecgDatacount++ ;
            	}else if(count>9210){
            		//&&count<=9280
                	if(count==9216){
                    	//平均心率
                		electrocardiogram.setAverageHeartRate(tempbyte) ;
                	}
                	//滤波模式
                	if(count==9284){
                		//滤波模式
                		electrocardiogram.setFilteringPattern(tempbyte==0 ?"一般模式":"增强模式") ;
                	}
                	//分析结果
                	if(count==9225){
                		electrocardiogram.setResultStatus(tempbyte) ;
                		electrocardiogram.setResult(GetEcgResult(tempbyte)) ;
                		if(tempbyte>0){
                			electrocardiogram.setState(1) ;//异常
                		}else{
                			electrocardiogram.setState(0) ;//正常
                		}
                		electrocardiogram.setDispose(0) ;
                	}
                	//测量模式
                	if(count==9229){
                		//如果count是0：快速测量  否则是连续测量
                		electrocardiogram.setMeasurementPattern(tempbyte==0 ?"快速测量":"连续测量") ;
                	}	
            	}
                count++ ;                
            }            
            
            String testDate =Integer.valueOf(toFullBinaryString(dateTime[1])+toFullBinaryString(dateTime[0]),2)+"-"+dateTime[2]+"-"+dateTime[3]+" "+dateTime[4]+":"+dateTime[5]+":"+dateTime[6]  ;
            electrocardiogram.setTestDateTime(DateUtils.parseDate(testDate, "yyyy-MM-dd HH:mm:ss")) ;
                        	
            String imageUrl = fileName.replaceAll("scpFile", "electrocardiogramPNG").replaceAll(".scp", ".png") ;
            
            electrocardiogram = DrawImage(electrocardiogram,imageUrl,ecgData) ;
            
            in.close();
            
            return electrocardiogram ;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    public static Electrocardiogram readFileByPCb(String fileName) {
    	//小格线颜色
    	Color xlineColor = new Color(255,229,229) ;
    	//大格线颜色
    	Color blineColor = new Color(255,187,187) ;
    	//黑色
    	Color black = new Color(0,0,0) ;
    	//每5小格变化一下颜色
    	float mm_Per_GridLine = 5.0f;
    	//每毫伏等于10mm
    	float mm_Per_mV = 10f;
    	//每秒等于25毫米
    	float mm_Per_s = 25.0f;
    	//每英寸等于25.4毫米
    	float mm_Per_Inch = 25.4f;
    	//每毫米等于0.03937007874015748031496062992126英寸
    	float Inch_Per_mm = 1f / mm_Per_Inch;
    	//分辨率 dpi的值
    	float DpiX = 96.0f;
    	float DpiY = 96.0f;
    	//每秒走多少像素      每秒走25毫米*dpi(分辨率)*每毫米等于多少英寸              25.0f*96*0.03937007874015748031496062992126  = 94.488188976377952755905511811024
    	float fPixel_Per_s = mm_Per_s * DpiX * Inch_Per_mm;
    	//每u伏等于多少像素                每毫伏等于10毫米*dpi(分辨率)*每毫米等于多少英寸       1毫伏=1000微伏    10f*96*0.03937007874015748031496062992126*0.001 = 0.03779527559055118110236220472441
    	float fPixel_Per_uV = mm_Per_mV * DpiY * Inch_Per_mm * 0.001f;
    	//一毫米*分辨率 每毫米的像素数   0.03937007874015748031496062992126*96 = 3.779527559055118110236220472441    72*0.03937007874015748031496062992126
    	float fPixel_Per_mm = DpiY * Inch_Per_mm;    	
    	//每条线时间
    	float time = 10;//时间 秒
    	//每秒走25毫米*10秒    250Mm  25cm
    	float lenght_mm = mm_Per_s * time;//打点长度，750 mm
    	//X轴点数    250mm * 分辨率96 * 毫米转换成英寸*0.03937007874015748031496062992126    =  944.88188976377952755905511811024 个像素
    	float lenght_pixel = lenght_mm * DpiX * Inch_Per_mm;//打点长度，像素
    	//每个ECG数据的像素点数  944.88188976377952755905511811024/1500 = 0.62992125984251968503937007874016   lenght_pixel / 1750;
    	float step = lenght_pixel / 1675;
    	float h_mm = 35;////每段的高度 毫米
    	//每段高度的像素  35mm高度是多少像素 3.779527559055118110236220472441 * 35 = 132.28346456692913385826771653544 个像素
    	int h = (int)Math.ceil(fPixel_Per_mm * h_mm);////每段的高度 像素
    	//每段的宽度像素数
    	int w = (int)Math.ceil(lenght_pixel) + 1;////每段的宽度 像素   	
        int width = 1024;    
        int height = 450;    
        int left = 39, top = 10;    
        
    	
        File tmpfile = new File(fileName);
        InputStream in = null;
        
        try {
            // 一次读一个字节
            in = new FileInputStream(tmpfile);
            int tempbyte;
            int count = 0 ;        
            int tmp = 0 ;
            StringBuilder sb = new StringBuilder("") ;            
            
	        Electrocardiogram electrocardiogram  =  new Electrocardiogram();
            int[] dian = new int[5025] ;
            
            electrocardiogram.setUploadDateTime(new Date()) ;
            electrocardiogram.setTestDateTime(new Date()) ;
            
            while ((tempbyte = in.read()) != -1){
            	count ++ ;  
            	if(count%4==0){
            		if(tmp<5025){
            			dian[tmp] = (tempbyte-127) ;
                		tmp ++ ;
            		}else if(tmp==5025) {
            			//此处为心电图 结果 和平均 心率 
                		electrocardiogram.setAverageHeartRate(tempbyte) ;
                		tmp ++ ;
            		}else{
            			//此处为心电图 结果 和平均 心率 
            			electrocardiogram.setResultStatus(tempbyte) ;
                		electrocardiogram.setState(tempbyte==0?0:1);//0为正常 1为异常
                		electrocardiogram.setDispose(0) ;
                		
                		electrocardiogram.setResult(GetEcgResult(tempbyte)) ;
                		tmp ++ ;
            		}
            	}
            }
            
            
            String imageUrl = fileName.replaceAll("scpFile", "electrocardiogramPNG").replaceAll(".scp", ".png") ;
            
            File file = new File(imageUrl);    
            
    		if(!file.exists()) //目录不存在则创建
    			file.mkdirs();
    		
            int offset = 0;
            float AVM = 11.71875f;
            float gain = 3.5f;
            int midian = 2048;     
            
            Font font = new Font("Serif", Font.BOLD, 10);    
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);    
            
            
            //大背景区
            Graphics2D g1 = (Graphics2D)bi.getGraphics();    
            g1.setBackground(new Color(248,247,243));    
            g1.clearRect(0, 0, width, height);    
            g1.setPaint(Color.RED);    
            
	        for (int i = 0; i < 3; i++){
	        	
		        float y_offset = ((4f / 7f) * fPixel_Per_mm * h_mm + top) * (i + 1) + (3f / 7f) * fPixel_Per_mm * h_mm * i;
		        
		        Graphics2D g2 = (Graphics2D)bi.getGraphics();    
		        /***** 平移 ****/ 
		        AffineTransform at=new AffineTransform();
		        at.translate(left,top * (i + 1) + i * h);
		        
		        g2.transform(at) ;
		        
		        
		        GradientPaint rrrr =  new GradientPaint(0, 0,new Color(255,187,187), 100, 100,new Color(0,0,0));
		        g2.setPaint(rrrr) ;
		        
		        float ww = 0 ;
		        //绘制网格水平线
		        for (int k = 0; k < h_mm+1; k++) {
		        	//每5格进行颜色变化
		        	if(k%5==0){
		        		g2.setColor(blineColor) ;  
		        	}else{
		                g2.setColor(xlineColor) ;         		
		        	}
		        	g2.drawLine(0,getInt(k*fPixel_Per_mm),(int)lenght_pixel,getInt(k*fPixel_Per_mm)) ;
		            
				}
		        //绘制网格竖线
		        for (int j = 0; j < lenght_mm + 1; j++){
		        	//每5格进行颜色变化
		        	if(j%5==0){
		        		g2.setColor(blineColor) ;  
		        	}else{
		                g2.setColor(xlineColor) ;         		
		        	}        	
		            g2.drawLine(getInt(j*fPixel_Per_mm), 0, getInt(j*fPixel_Per_mm), getInt(fPixel_Per_mm*h_mm)) ;
		        }
		        
		        //心电图数据
		        Graphics2D g3 = (Graphics2D)bi.getGraphics();    
		        AffineTransform at3=new AffineTransform();	
		        
		        //心电图准线
//	        	System.out.println(left+"#########"+y_offset);
	        	
		        //心电图起始
	        	Graphics2D g4 = (Graphics2D)bi.getGraphics();   
	        	if(i==0){
		        	at3.translate(left,y_offset);
		        	
		        	g4.transform(at3) ;
		        	g4.setColor(Color.black) ;
		        	g4.drawLine( -14, 0, -12, 0);
		        	g4.setColor(Color.black) ;        	
		            g4.drawLine( -12, 0, -12, getInt(-10*fPixel_Per_mm));
		        	g4.setColor(Color.black) ;
		            g4.drawLine( -12, getInt(-10*fPixel_Per_mm), -2, getInt(-10*fPixel_Per_mm));
		        	g4.setColor(Color.black) ;
		            g4.drawLine( -2, getInt(-10*fPixel_Per_mm), -2, 0);
		        	g4.setColor(Color.black) ;
		            g4.drawLine( -2, 0, 0, 0);    
		        }else if(i==1){
		        	/****
		        	 * 第二段 和 第三段 也应该使用	at3.translate(left,y_offset);
		        	 * 这个算法，但是由于java解释浮点数 跟C不一样
		        	 * 所以采用手动算出
		        	 */
		        	at3.translate(left,229);
		        }else if(i==2){
		        	at3.translate(left,372);
		        }   
	            g3.transform(at3) ;
	        	
		        
	            /**********/
		        for (int n = 0; n < dian.length; n++){
		            if (n != 0 && n % 1675 == 0){
		                offset += n;
		                break ;
		            }
		            if (n < 1675 - 1){
		            	//黑色  ,x轴 位置 ,  N个数 *每个ecg长度     (2048-2245)*0.805*0.02834645669291338582677165354331*3.5      
		            	g3.setColor(black) ;
		            	g3.drawLine(getInt(n*step),getInt((- dian[n + offset]) * AVM * fPixel_Per_uV), 
		            				getInt((n + 1) * step),getInt((- dian[n + 1 + offset]) * AVM * fPixel_Per_uV ));	
		            }		            
		        }
		        
	        }
	        //添加文字
	        Graphics2D g5 = (Graphics2D)bi.getGraphics();
	        g5.setColor(Color.black) ;
	        g5.drawString("10mm/mV   25mm/s",45,442);     
            
	        
			String client_upload_base = SystemConfig.getString("client_upload_base");
			imageUrl = imageUrl.replaceAll(client_upload_base+"/attached", "bskimages") ;
	        electrocardiogram.setAttachmentUrl(imageUrl) ;
	        
	        ImageIO.write(bi, "png", file);    

//            System.out.println("共计"+count);
            
            return electrocardiogram ;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		return null ; 

    }
    /**
     * 将 int 类型数据转成二进制的字符串，不足 int 类型位数时在前面添“0”以凑足位数
     * @param num
     * @return
     */
    public static String toFullBinaryString(int num) {
        char[] chs = new char[8];
        for(int i = 0; i < 8; i++) {
            chs[8 - 1 - i] = (char)(((num >> i) & 1) + '0');
        }
        return new String(chs);        
    }
	/**
	 * 心电图
	 * @param electrocardiogram
	 * @param imageUrl
	 * @param ecgData
	 * @return
	 * @throws IOException
	 */
    public static Electrocardiogram DrawImage(Electrocardiogram electrocardiogram,String imageUrl,int[] ecgData) throws IOException{
    	//将9000个点的ECG字节转换成 4500个ECG数据点
    	int[] myint = getECGDate(ecgData) ;
    	
    	//小格线颜色
    	Color xlineColor = new Color(255,229,229) ;
    	//大格线颜色
    	Color blineColor = new Color(255,187,187) ;
    	//黑色
    	Color black = new Color(0,0,0) ;
    	//每5小格变化一下颜色
    	float mm_Per_GridLine = 5.0f;
    	//每毫伏等于10mm
    	float mm_Per_mV = 10f;
    	//每秒等于25毫米
    	float mm_Per_s = 25.0f;
    	//每英寸等于25.4毫米
    	float mm_Per_Inch = 25.4f;
    	//每毫米等于0.03937007874015748031496062992126英寸
    	float Inch_Per_mm = 1f / mm_Per_Inch;
    	//分辨率 dpi的值
    	float DpiX = 96.0f;
    	float DpiY = 96.0f;
    	//每秒走多少像素      每秒走25毫米*dpi(分辨率)*每毫米等于多少英寸              25.0f*96*0.03937007874015748031496062992126  = 94.488188976377952755905511811024
    	float fPixel_Per_s = mm_Per_s * DpiX * Inch_Per_mm;
    	//每u伏等于多少像素                每毫伏等于10毫米*dpi(分辨率)*每毫米等于多少英寸       1毫伏=1000微伏    10f*96*0.03937007874015748031496062992126*0.001 = 0.03779527559055118110236220472441
    	float fPixel_Per_uV = mm_Per_mV * DpiY * Inch_Per_mm * 0.001f;
    	//一毫米*分辨率 每毫米的像素数   0.03937007874015748031496062992126*96 = 3.779527559055118110236220472441    72*0.03937007874015748031496062992126
    	float fPixel_Per_mm = DpiY * Inch_Per_mm;    	
    	//每条线时间
    	float time = 10;//时间 秒
    	//每秒走25毫米*10秒    250Mm  25cm
    	float lenght_mm = mm_Per_s * time;//打点长度，750 mm
    	//X轴点数    250mm * 分辨率96 * 毫米转换成英寸*0.03937007874015748031496062992126    =  944.88188976377952755905511811024 个像素
    	float lenght_pixel = lenght_mm * DpiX * Inch_Per_mm;//打点长度，像素
    	//每个ECG数据的像素点数  944.88188976377952755905511811024/1500 = 0.62992125984251968503937007874016   lenght_pixel / 1500;
    	float step = lenght_pixel / 1500;
    	float h_mm = 35;////每段的高度 毫米
    	//每段高度的像素  35mm高度是多少像素 3.779527559055118110236220472441 * 35 = 132.28346456692913385826771653544 个像素
    	int h = (int)Math.ceil(fPixel_Per_mm * h_mm);////每段的高度 像素
    	//每段的宽度像素数
    	int w = (int)Math.ceil(lenght_pixel) + 1;////每段的宽度 像素   	
        int width = 1024;    
        int height = 450;    
        int left = 39, top = 10;    
        File file = new File(imageUrl);    
        
		if(!file.exists()) //目录不存在则创建
			file.mkdirs();
        
        int offset = 0;
        float AVM = 0.805f;
        float gain = 3.5f;
        int midian = 2048;        
        
        Font font = new Font("Serif", Font.BOLD, 10);    
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);    
        
        

        //大背景区
        Graphics2D g1 = (Graphics2D)bi.getGraphics();    
        g1.setBackground(new Color(248,247,243));    
        g1.clearRect(0, 0, width, height);    
        g1.setPaint(Color.RED);    
        
        for (int i = 0; i < 3; i++){
        	
	        float y_offset = ((4f / 7f) * fPixel_Per_mm * h_mm + top) * (i + 1) + (3f / 7f) * fPixel_Per_mm * h_mm * i;
	        
	        Graphics2D g2 = (Graphics2D)bi.getGraphics();    
	        /***** 平移 ****/ 
	        AffineTransform at=new AffineTransform();
	        at.translate(left,top * (i + 1) + i * h);
	        
	        g2.transform(at) ;
	        
	        
	        GradientPaint rrrr =  new GradientPaint(0, 0,new Color(255,187,187), 100, 100,new Color(0,0,0));
	        g2.setPaint(rrrr) ;
	        
	        float ww = 0 ;
	        //绘制网格水平线
	        for (int k = 0; k < h_mm+1; k++) {
	        	//每5格进行颜色变化
	        	if(k%5==0){
	        		g2.setColor(blineColor) ;  
	        	}else{
	                g2.setColor(xlineColor) ;         		
	        	}
	        	g2.drawLine(0,getInt(k*fPixel_Per_mm),(int)lenght_pixel,getInt(k*fPixel_Per_mm)) ;
	            
			}
	        //绘制网格竖线
	        for (int j = 0; j < lenght_mm + 1; j++){
	        	//每5格进行颜色变化
	        	if(j%5==0){
	        		g2.setColor(blineColor) ;  
	        	}else{
	                g2.setColor(xlineColor) ;         		
	        	}        	
	            g2.drawLine(getInt(j*fPixel_Per_mm), 0, getInt(j*fPixel_Per_mm), getInt(fPixel_Per_mm*h_mm)) ;
	        }
	        
	        //心电图数据
	        Graphics2D g3 = (Graphics2D)bi.getGraphics();    
	        AffineTransform at3=new AffineTransform();	
	        
	        //心电图准线
        	at3.translate(left,getInt(y_offset));
        	
	        //心电图起始
        	Graphics2D g4 = (Graphics2D)bi.getGraphics();   
        	if(i==0){
	        	g4.transform(at3) ;
	        	g4.setColor(Color.black) ;
	        	g4.drawLine( -14, 0, -12, 0);
	        	g4.setColor(Color.black) ;        	
	            g4.drawLine( -12, 0, -12, getInt(-10*fPixel_Per_mm));
	        	g4.setColor(Color.black) ;
	            g4.drawLine( -12, getInt(-10*fPixel_Per_mm), -2, getInt(-10*fPixel_Per_mm));
	        	g4.setColor(Color.black) ;
	            g4.drawLine( -2, getInt(-10*fPixel_Per_mm), -2, 0);
	        	g4.setColor(Color.black) ;
	            g4.drawLine( -2, 0, 0, 0);    
	        }   
            g3.transform(at3) ;
        	
	        
	        for (int n = 0; n < myint.length; n++){
	            if (n != 0 && n % 1500 == 0){
	                offset += n;
	                break ;
	            }
	            if (n < 1500 - 1){
	            	//黑色  ,x轴 位置 ,  N个数 *每个ecg长度     (2048-2245)*0.805*0.02834645669291338582677165354331*3.5      
	            	g3.setColor(black) ;
	            	g3.drawLine(getInt(n*step),getInt((midian - myint[n + offset]) * AVM * fPixel_Per_uV * gain), 
	            				getInt((n + 1) * step),getInt((midian - myint[n + 1 + offset]) * AVM * fPixel_Per_uV * gain));	
	            		            	
	            }
	            
	        }
        }
        //添加文字
        Graphics2D g5 = (Graphics2D)bi.getGraphics();
        g5.setColor(Color.black) ;
        g5.drawString("10mm/mV   25mm/s",45,442);     
        
		String client_upload_base = SystemConfig.getString("client_upload_base");
		imageUrl = imageUrl.replaceAll(client_upload_base+"/attached", "bskimages") ;
        electrocardiogram.setAttachmentUrl(imageUrl) ;
        ImageIO.write(bi, "png", file);    

    	return electrocardiogram ;
    }
    //四舍五入
    public static int getInt(float aaa){
    	return	Integer.valueOf(new BigDecimal(aaa).setScale(0, BigDecimal.ROUND_HALF_UP).toString()) ;
    }
    /**
     * 将9000个字节的ECG数据  转换成4500个ECG点 按照低字节在前   高字节在后进行 转换
     * @param ecgData
     * @return
     */
    public static int[] getECGDate(int[] ecgData){
    	
        int[] myint = new int[4500] ;
        int myintTmp = 0 ;
        for (int i = 0; i < ecgData.length; i = i+2) {
        	//每2个字节为一个ECG数据包    int 类型数据转成二进制的字符串
        	String data  = toFullBinaryString(ecgData[i+1])+toFullBinaryString(ecgData[i]) ;
        	//将2进制的数据装换成10进制的数据 
            int tmp = Integer.valueOf(data,2) ;
        	myint[myintTmp] = tmp ;
        	myintTmp ++ ;
		}
    	return myint ;
    }
    /**
     * 返回结果数据
     * @param key
     * @return
     * 
     * 
     */
    public static String GetEcgResult(int key){
    		HashMap<Integer, String> ecgResults = new HashMap<Integer, String>() ;
    		/****
    		ecgResults.put(0, "心跳节律无异常");
	        ecgResults.put(1, "疑似心跳稍快请注意休息");
	        ecgResults.put(2, "疑似心跳过快请注意休息");
	        ecgResults.put(3, "疑似阵发性心跳过快请咨询医生");
	        ecgResults.put(4, "疑似心跳稍缓请注意休息");
	        ecgResults.put(5, "疑似心跳过缓请注意休息");
	        ecgResults.put(6, "疑似心跳间期缩短请咨询医生");
	        ecgResults.put(7, "疑似心跳间期不规则请咨询医生");
	        ecgResults.put(8, "疑似心跳稍快伴有心跳间期缩短请咨询医生");
	        ecgResults.put(9, "疑似心跳稍缓伴有心跳间期缩短请咨询医生");
	        ecgResults.put(10, "疑似心跳稍缓伴有心跳间期不规则请咨询医生");
	        ecgResults.put(11, "波形有漂移请重新测量");
	        ecgResults.put(12, "疑似心跳过快伴有波形漂移请咨询医生");
	        ecgResults.put(13, "疑似心跳过缓伴有波形漂移请咨询医生");
	        ecgResults.put(14, "疑似心跳间期缩短伴有波形漂移请咨询医生");
	        ecgResults.put(15, "疑似心跳间期不规则伴有波形漂移请咨询医生");
	        ecgResults.put(16, "信号较差，请重新测量");
	        ****/
	        ecgResults.put(0, "您好！本次检测提示心跳节律无异常。");
	        ecgResults.put(1, "您好！本次检测提示心跳稍快，请确保在安静状态下测量，再上传，以便提供非常好的服务。");
	        ecgResults.put(2, "您好！本次检测提示心跳过快，请确保在安静状态下测量，再上传，建议尽快咨询专业人士。");
	        ecgResults.put(3, "您好！本次检测发现疑似阵发性心跳过快，如有不适，请及时就医。");
	        ecgResults.put(4, "您好！您本次检测提示疑似心跳稍缓，请注意身体。");
	        ecgResults.put(5, "您好！您本次检测提示疑似心跳过缓，如伴不适，建议尽快咨询专业人士或及时就医。");
	        ecgResults.put(6, "您好！您本次检测提示疑似心跳间期缩短，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(7, "您好！您本次检测提示疑似心跳间期不规则，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(8, "您好！您本次检测提示疑似心跳稍快伴有心跳间期缩短，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(9, "您好！您本次检测提示疑似心跳稍缓伴有心跳间期缩短，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(10, "您好！您本次检测提示疑似心跳稍缓伴有心跳间期不规则，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(11, "您本次检测提示波形有漂移请重新测量，请结合既往病史，必要时就医。");
	        ecgResults.put(12, "您好！您本次检测提示疑似心跳过快伴有波形漂移，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(13, "您好！您本次检测提示疑似心跳过缓伴有波形漂移，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(14, "您好！您本次检测提示疑似心跳间期缩短伴有波形漂移，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(15, "您好！您本次检测提示疑似心跳间期不规则伴有波形漂移，建议尽快咨询专业人士或就医检查。");
	        ecgResults.put(16, "您好！信号较差，查看是否电池电量低，接触不良造成，请重新检查。");
	        
	        return ecgResults.get(key);    	
    }
    
    public static void main(String[] args) {
//    	String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-06-28 17-10-39.scp";
//    	String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-07-10 09-50-59.scp";
//    	String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-07-10 09-54-30.scp";
//    	String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-07-10 09-56-04.scp";
    	
//    	String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-07-10 09-56-44.scp" ;
//    	String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-07-10 09-58-56.scp" ;
    	//String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-07-11 15-23-14.scp" ;
//    	String fileName = "D:/2013-06-25 15-42-39.scp" ;
    	
    	
//    	String fileName = "D:/2013-11-11 15-50-36.ecg" ;
    	String fileName = "D:/20131112142752.scp" ;
    	
    	ReadFromFileDrawImage.readFileByPCb(fileName) ;
         
    	
    	//String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-06-25 15-42-39.scp";
    	//String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-06-25 15-49-00.scp";
    	//String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2012-01-01 15-32-03.scp";
    	//String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/2013-06-25 15-52-52.scp";
    	//String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/14.scp";
    	
    	//String fileName = "D:/Workspaces/MyEclipse 8.5/chch/test/test/my/test2.xml";
    	
    	//String fileName = "D:/111.SCP";
        
    	//ReadFromFileDrawImage.readFileByBytes(fileName);
    	
    	//ReadFromFile.readFileByChars(fileName);
        //ReadFromFile.readFileByLines(fileName);
       //ReadFromFile.readFileByRandomAccess(fileName);
    }
}
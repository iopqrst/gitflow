package com.bskcare.ch.poi.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel 操作类
 * 
 * @author houzhiqing
 * 
 */
@SuppressWarnings("unchecked")
public class ExcelUtil {

	private static final String POSITION_TITLE = "title";

	private static final String POSITION_BODY = "body";

	private static ExcelUtil eu = new ExcelUtil();

	private ExcelUtil() {
	}

	public static ExcelUtil getInstance() {
		return eu;
	}

	/**
	 * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
	 * 
	 * @param outPath
	 *            输出路径
	 * @param objs
	 *            数据源
	 * @param clz
	 *            类
	 * @param sheetName
	 *            分sheet导出是sheet的名字 ， 如 “sheet” -> sheet1,sheet2...
	 * @param pageSize
	 *            每个sheet要显示多少条数据
	 */
	public void exportObj2Excel(String outPath, List objs, Class clz,
			String sheetName, int pageSize) {
		Workbook wb = handleObj2Excel(objs, clz, sheetName, pageSize);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(outPath);
			wb.write(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 导出对象到Excel，不是基于模板的，直接新建一个Excel完成导出，基于路径的导出
	 * 
	 * @param outPath
	 *            输出路径
	 * @param objs
	 *            数据源
	 * @param clz
	 *            类
	 * @param sheetName
	 *            分sheet导出是sheet的名字 ， 如 “sheet” -> sheet1,sheet2...
	 * @param pageSize
	 *            每个sheet要显示多少条数据
	 */
	public HSSFWorkbook handleObj2Excel(List objs, Class clz, String sheetName,
			int pageSize) {
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook();
			// TODO 获取表头
			List<ExcelHeader> headers = getHeaderList(clz);
			Collections.sort(headers);

			if (null != objs && objs.size() > 0) {

				int sheetCount = objs.size() % pageSize == 0 ? objs.size()
						/ pageSize : objs.size() / pageSize + 1;

				for (int i = 1; i <= sheetCount; i++) {

					HSSFSheet sheet = null;
					if(!StringUtils.isEmpty(sheetName)) {
						sheet = wb.createSheet(sheetName + i);
					} else {
						sheet = wb.createSheet();
					}
					
					HSSFRow row = sheet.createRow(0);

					// 写标题
					CellStyle titleStyle = setCellStyle(wb, POSITION_TITLE);
					for (int m = 0; m < headers.size(); m++) {
						HSSFCell cell = row.createCell(m);
						cell.setCellStyle(titleStyle);
						cell.setCellValue(headers.get(m).getTitle());
						sheet.setColumnWidth(m, 5000); // 设置每列的宽度
					}

					// 写数据
					Object obj = null;
					CellStyle bodyStyle = setCellStyle(wb, POSITION_BODY);
					int begin = (i - 1) * pageSize;
					int end = (begin + pageSize) > objs.size() ? objs.size()
							: (begin + pageSize);

					System.out.println("begin:" + begin + ",end=" + end);

					int rowCount = 1;
					for (int n = begin; n < end; n++) {
						row = sheet.createRow(rowCount);
						rowCount++;
						obj = objs.get(n);
						for (int x = 0; x < headers.size(); x++) {
							Cell cell = row.createCell(x);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(BeanUtils.getProperty(obj,
									getMethodName(headers.get(x))));
						}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("生成excel失败");
		}
		return wb;
	}

	/**
	 * 根据标题获取相应的方法名称
	 * 
	 * @param eh
	 * @return
	 */
	private String getMethodName(ExcelHeader eh) {
		String mn = eh.getMethodName().substring(3);
		mn = mn.substring(0, 1).toLowerCase() + mn.substring(1);
		return mn;
	}

	/**
	 * 获取excel标题列表
	 * 
	 * @param clz
	 * @return
	 */
	private List<ExcelHeader> getHeaderList(Class clz) {
		List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
		Method[] ms = clz.getDeclaredMethods();
		for (Method m : ms) {
			String mn = m.getName();
			if (mn.startsWith("get")) {
				if (m.isAnnotationPresent(ExcelResources.class)) {
					ExcelResources er = m.getAnnotation(ExcelResources.class);
					headers.add(new ExcelHeader(er.title(), er.order(), mn));
				}
			}
		}
		return headers;
	}

	/**
	 * 设置单元格样式
	 * 
	 * @param position
	 *            ["body","title"]
	 */
	private static CellStyle setCellStyle(Workbook workBook, String position) {

		CellStyle cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置单元格字体
		Font headerFont = workBook.createFont(); // 字体
		if (POSITION_TITLE.equals(position)) {
			headerFont.setFontHeightInPoints((short) 12);
		} else {
			headerFont.setFontHeightInPoints((short) 10);
		}
		headerFont.setFontName("宋体");
		if (POSITION_TITLE.equals(position))
			headerFont.setBoldweight((short) 10);
		cellStyle.setFont(headerFont);
		cellStyle.setWrapText(true);

		cellStyle.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
		// 设置单元格边框及颜色
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setWrapText(true);

		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index); // 设置边框颜色
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);

		return cellStyle;
	}
}

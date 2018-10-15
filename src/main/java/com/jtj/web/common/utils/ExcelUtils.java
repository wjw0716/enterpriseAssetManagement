package com.jtj.web.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static List<Map<String, Object>> imports(FileInputStream is, Map<String, String> keys) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			/* 获取第0行标题 */
			Row row0 = sheet.getRow(0);
			/* 遍历每一列 */
			for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
				Map<String, Object> obj = new HashMap<>();
				Row row = sheet.getRow(r);
				for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
					Cell titleCell = row0.getCell(c);
					if (null == titleCell) {
						continue;
					}
					RichTextString title = titleCell.getRichStringCellValue();

					Cell cell = row.getCell(c);
					CellType cellType = cell.getCellType();
					String value = getCellValue(cell, cellType).trim();

					for (Entry<String, String> entry : keys.entrySet()) {
						String val = entry.getValue();
						String key = entry.getKey();
						if (val.equals(title.getString())) {
							obj.put(key, value);
						}
					}
				}
				result.add(obj);
			}
			workbook.close();
		} catch (Exception e) {
			throw new RuntimeException("导如excel失败,[" + e.getMessage() + "]");
		}
		return result;
	}

	/**
	 * 获取单元格数据
	 * 
	 * @param cell
	 * @param cellType
	 * @return
	 */
	private static String getCellValue(Cell cell, CellType cellType) {
		switch (cellType) {
		case STRING:
			return cell.getRichStringCellValue().getString();
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return String.valueOf(cell.getDateCellValue().getTime());
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return String.valueOf(cell.getCellFormula());
		case BLANK:
			return "";
		default:
			return "";
		}
	}

	public static List<Map<String, Object>> imports2007(FileInputStream is, Map<String, String> keys) {
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			/* 获取第0行标题 */
			Row row0 = sheet.getRow(0);
			/* 遍历每一列 */
			for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
				Map<String, Object> obj = new HashMap<>();
				Row row = sheet.getRow(r);
				for (int c = 0; c < row.getPhysicalNumberOfCells(); c++) {
					Cell titleCell = row0.getCell(c);
					if (null == titleCell) {
						continue;
					}
					RichTextString title = titleCell.getRichStringCellValue();

					Cell cell = row.getCell(c);
					CellType cellType = cell.getCellType();
					String value = getCellValue(cell, cellType).trim();

					for (Entry<String, String> entry : keys.entrySet()) {
						String val = entry.getValue();
						String key = entry.getKey();
						if (val.equals(title.getString())) {
							obj.put(key, value);
						}
					}
				}
				result.add(obj);
			}
			workbook.close();
		} catch (Exception e) {
			throw new RuntimeException("导如excel失败,[" + e.getMessage() + "]");
		}
		return result;
	}

	public static byte[] exports(String sheetName, List<Map<String, Object>> list, Map<String, String> keys) {
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(sheetName);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			int counter=1;
			 Row row0 = sheet.createRow(0);
			for (Map<String, Object> map : list) {
				 Row row = sheet.createRow(counter);
				 
				int cellCounter=0;
				for (Entry<String, Object> entry : map.entrySet()) {
					Object val = entry.getValue();
					
					if(counter==1) {
						String key = entry.getKey();
						row0.createCell(cellCounter).setCellValue(keys.get(key));
						row.createCell(cellCounter).setCellValue(String.valueOf(val));
					}else {
						row.createCell(cellCounter).setCellValue(String.valueOf(val));
					}
					cellCounter++;
				}
				counter++;
			}
			wb.write(stream);
			stream.flush();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("导出excel失败,[" + e.getMessage() + "]");
		}finally{
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return stream.toByteArray();
	}

	/*
	 * public static void exports2007(String sheetName,List<?>
	 * list,HttpServletResponse response) {
	 * 
	 * if(list!=null&&list.size()<1) throw new RuntimeException("导出excel失败！"); try {
	 * ServletOutputStream out = response.getOutputStream(); XSSFWorkbook wb = new
	 * XSSFWorkbook(); XSSFSheet sheet=wb.createSheet(sheetName); for (int i=
	 * 0;i<list.size();i++) { Object obj = list.get(i); Class clazz =
	 * obj.getClass(); if(i== 0) { XSSFRow row=sheet.createRow(i); Field[] fields =
	 * clazz.getDeclaredFields(); int tmp = 0; for (Field field :fields) { XSSFCell
	 * cell = row.createCell(tmp); ExcelField excelFiled =
	 * field.getAnnotation(ExcelField.class); if (excelFiled ==
	 * null||excelFiled.isOnlyImport()) continue;
	 * cell.setCellValue(excelFiled.value()); tmp++; } } XSSFRow
	 * row=sheet.createRow(i+1); Field[] fields = clazz.getDeclaredFields(); int tmp
	 * = 0; for (Field field :fields) { ExcelField excelFiled =
	 * field.getAnnotation(ExcelField.class); if (excelFiled ==
	 * null||excelFiled.isOnlyImport()){continue;} XSSFCell cell =
	 * row.createCell(tmp); PropertyDescriptor pd = new
	 * PropertyDescriptor(field.getName(),clazz); Method getMethod =
	 * pd.getReadMethod(); Object o = getMethod.invoke(obj);
	 * if(!StrKit.isBlank(excelFiled.dateFormat())&& o instanceof Date)
	 * cell.setCellValue(DateUtils.format((Date)o,excelFiled.dateFormat())); else
	 * cell.setCellValue(o!=null?excelFiled.isNullDefaultValue():String.valueOf(o));
	 * tmp++; } } response.reset(); response.setHeader("Content-disposition",
	 * "attachment;filename="+ new String(sheetName.getBytes("utf-8"),
	 * "ISO8859-1")+".xlsx"); response.setContentType("application/msexcel");
	 * wb.write(out); wb.close(); out.flush(); out.close();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); throw new
	 * RuntimeException("导出excel失败,["+e.getMessage()+"]"); } }
	 */

}

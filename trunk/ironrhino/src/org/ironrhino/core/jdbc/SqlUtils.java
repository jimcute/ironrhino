package org.ironrhino.core.jdbc;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class SqlUtils {

	public static String refineSql(String sql) {
		sql = sql.trim();
		if (sql.endsWith(";"))
			sql = sql.substring(0, sql.length() - 1);
		return new StringBuilder(sql.length() + 2).append("\n").append(sql)
				.append("\n").toString();
	}

	public static String clearComments(String sql) {
		if (StringUtils.isBlank(sql))
			return sql;
		return LINE_COMMENTS_PATTERN
				.matcher(BLOCK_COMMENTS_PATTERN.matcher(sql).replaceAll(""))
				.replaceAll("\n").replaceAll("\n+", "\n").trim();
	}

	public static String highlight(String sql) {
		if (StringUtils.isBlank(sql))
			return sql;
		return PARAMETER_PATTERN.matcher(
				LINE_COMMENTS_PATTERN.matcher(
						BLOCK_COMMENTS_PATTERN.matcher(sql).replaceAll(
								"<span class=\"comment\">$0</span>"))
						.replaceAll("\n<span class=\"comment\">$1</span>\n"))
				.replaceAll("<strong>$0</strong>");
	}

	public static Set<String> extractParameters(String sql) {
		if (StringUtils.isBlank(sql))
			return Collections.emptySet();
		sql = clearComments(sql);
		Set<String> names = new LinkedHashSet<String>();
		Matcher m = PARAMETER_PATTERN.matcher(sql);
		while (m.find())
			names.add(m.group(1).substring(1));
		return names;
	}

	public static Set<String> extractTables(String sql, String quoteString) {
		return extractTables(sql, quoteString, "from");
	}

	public static Set<String> extractTables(String sql, String quoteString,
			String frontKeyword) {
		if (StringUtils.isBlank(sql))
			return Collections.emptySet();
		sql = clearComments(sql);
		Pattern tablePattern = Pattern.compile(frontKeyword + "\\s+([\\w\\."
				+ quoteString + ",\\s]+)", Pattern.CASE_INSENSITIVE);
		Set<String> names = new LinkedHashSet<String>();
		Matcher m = tablePattern.matcher(sql);
		while (m.find()) {
			String arr[] = m.group(1).split(",");
			for (String s : arr) {
				names.add(s.trim().split("\\s+")[0]);
			}
		}
		return names;
	}

	private static final Pattern PARAMETER_PATTERN = Pattern
			.compile("(:\\w*)(,|;|\\)|\\s|\\||\\+|$)");

	private static final Pattern BLOCK_COMMENTS_PATTERN = Pattern
			.compile("/\\*(?:.|[\\n\\r])*?\\*/");

	private static final Pattern LINE_COMMENTS_PATTERN = Pattern
			.compile("\r?\n?([ \\t]*--.*)\r?(\n|$)");

}
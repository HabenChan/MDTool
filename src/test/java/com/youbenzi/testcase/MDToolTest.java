package com.youbenzi.testcase;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.youbenzi.mdtool.tool.MDTool;

public class MDToolTest {

	@Test
	public void testHeaderH1() {
		String result = MDTool.markdown2Html("# Title");
		Assert.assertTrue(result.contains("<h1>Title</h1>"));
	}

	@Test
	public void testHeaderH2() {
		String result = MDTool.markdown2Html("## Subtitle");
		Assert.assertTrue(result.contains("<h2>Subtitle</h2>"));
	}

	@Test
	public void testHeaderH3() {
		String result = MDTool.markdown2Html("### Section");
		Assert.assertTrue(result.contains("<h3>Section</h3>"));
	}

	@Test
	public void testHeaderH4() {
		String result = MDTool.markdown2Html("#### SubSection");
		Assert.assertTrue(result.contains("<h4>SubSection</h4>"));
	}

	@Test
	public void testHeaderH5() {
		String result = MDTool.markdown2Html("##### Minor");
		Assert.assertTrue(result.contains("<h5>Minor</h5>"));
	}

	@Test
	public void testHeaderH6() {
		String result = MDTool.markdown2Html("###### Small");
		Assert.assertTrue(result.contains("<h6>Small</h6>"));
	}

	@Test
	public void testBoldWithDoubleAsterisk() {
		String result = MDTool.markdown2Html("**bold text**");
		Assert.assertTrue(result.contains("<strong>bold text</strong>"));
	}

	@Test
	public void testBoldWithDoubleUnderscore() {
		String result = MDTool.markdown2Html("__bold text__");
		Assert.assertTrue(result.contains("<strong>bold text</strong>"));
	}

	@Test
	public void testItalicWithAsterisk() {
		String result = MDTool.markdown2Html("*italic text*");
		Assert.assertTrue(result.contains("<em>italic text</em>"));
	}

	@Test
	public void testItalicWithUnderscore() {
		String result = MDTool.markdown2Html("_italic text_");
		Assert.assertTrue(result.contains("<em>italic text</em>"));
	}

	@Test
	public void testStrikethrough() {
		String result = MDTool.markdown2Html("~~deleted text~~");
		Assert.assertTrue(result.contains("<del>deleted text</del>"));
	}

	@Test
	public void testInlineCode() {
		String result = MDTool.markdown2Html("`code`");
		Assert.assertTrue(result.contains("<code>code</code>"));
	}

	@Test
	public void testCodeBlock() {
		String content = "```\npublic class A {\n}\n```";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<pre>"));
		Assert.assertTrue(result.contains("<code>"));
		Assert.assertTrue(result.contains("public class A {"));
	}

	@Test
	public void testCodeBlockWithHtmlEntities() {
		String content = "```\n<div>hello</div>\n```";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("&lt;div&gt;hello&lt;/div&gt;"));
	}

	@Test
	public void testBlockquote() {
		String result = MDTool.markdown2Html("> quoted text");
		Assert.assertTrue(result.contains("<blockquote>"));
		Assert.assertTrue(result.contains("quoted text"));
	}

	@Test
	public void testBlockquoteMultiLine() {
		String content = "> line1\n> line2";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<blockquote>"));
		Assert.assertTrue(result.contains("line1"));
		Assert.assertTrue(result.contains("line2"));
	}

	@Test
	public void testLink() {
		String result = MDTool.markdown2Html("[Google](http://google.com)");
		Assert.assertTrue(result.contains("<a href=\"http://google.com\""));
		Assert.assertTrue(result.contains("Google"));
	}

	@Test
	public void testImage() {
		String result = MDTool.markdown2Html("![alt](http://example.com/img.png)");
		Assert.assertTrue(result.contains("<img src=\"http://example.com/img.png\""));
		Assert.assertTrue(result.contains("alt=\"alt\""));
	}

	@Test
	public void testTable() {
		String content = "| Name | Age |\n|------|-----|\n| Tom  | 20  |";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<table>"));
		Assert.assertTrue(result.contains("<th "));
		Assert.assertTrue(result.contains("Name</th>"));
		Assert.assertTrue(result.contains("Age</th>"));
		Assert.assertTrue(result.contains("<td "));
		Assert.assertTrue(result.contains("Tom<br/>"));
		Assert.assertTrue(result.contains("20<br/>"));
	}

	@Test
	public void testTableWithAlignment() {
		String content = "| Left | Center | Right |\n|:-----|:------:|------:|\n| a    | b      | c     |";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("align=\"left\""));
		Assert.assertTrue(result.contains("align=\"center\""));
		Assert.assertTrue(result.contains("align=\"right\""));
	}

	@Test
	public void testOrderedList() {
		String content = "1. first\n2. second\n3. third";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<ol>"));
		Assert.assertTrue(result.contains("<li>first"));
		Assert.assertTrue(result.contains("<li>second"));
		Assert.assertTrue(result.contains("<li>third"));
	}

	@Test
	public void testUnorderedListWithAsterisk() {
		String content = "* item1\n* item2";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<ul>"));
		Assert.assertTrue(result.contains("<li>item1"));
		Assert.assertTrue(result.contains("<li>item2"));
	}

	@Test
	public void testUnorderedListWithDash() {
		String content = "- item1\n- item2";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<ul>"));
		Assert.assertTrue(result.contains("<li>item1"));
		Assert.assertTrue(result.contains("<li>item2"));
	}

	@Test
	public void testUnorderedListWithPlus() {
		String content = "+ item1\n+ item2";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<ul>"));
		Assert.assertTrue(result.contains("<li>item1"));
		Assert.assertTrue(result.contains("<li>item2"));
	}

	@Test
	public void testTaskListUnchecked() {
		String content = "1. parent\n\t[ ] todo1\n\t[ ] todo2";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("unchecked_icon"));
	}

	@Test
	public void testTaskListChecked() {
		String content = "1. parent\n\t[x] done1\n\t[x] done2";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("checked_icon"));
	}

	@Test
	public void testNestedList() {
		String content = "1. parent\n    * child1\n    * child2";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("<ol>"));
		Assert.assertTrue(result.contains("<ul>"));
	}

	@Test
	public void testLineBreak() {
		String result = MDTool.markdown2Html("line1  \nline2");
		Assert.assertTrue(result.contains("<br/>"));
	}

	@Test
	public void testMixedBoldAndItalic() {
		String result = MDTool.markdown2Html("**bold** and *italic*");
		Assert.assertTrue(result.contains("<strong>bold</strong>"));
		Assert.assertTrue(result.contains("<em>italic</em>"));
	}

	@Test
	public void testPlainText() {
		String result = MDTool.markdown2Html("just plain text");
		Assert.assertTrue(result.contains("just plain text"));
	}

	@Test
	public void testEmptyString() {
		String result = MDTool.markdown2Html("");
		Assert.assertEquals("", result);
	}

	@Test
	public void testNullInput() {
		String result = MDTool.markdown2Html((String) null);
		Assert.assertNull(result);
	}

	@Test
	public void testMultipleParagraphs() {
		String content = "first line\n\nsecond line\n\nthird line";
		String result = MDTool.markdown2Html(content);
		Assert.assertTrue(result.contains("first line"));
		Assert.assertTrue(result.contains("second line"));
		Assert.assertTrue(result.contains("third line"));
	}

	@Test
	public void testFromFile() throws IOException {
		String result = MDTool.markdown2Html(new File(MDToolTest.class.getResource("/file.md").getFile()));
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
	}

	@Test
	public void testFromFileGBK() throws IOException {
		String result = MDTool.markdown2Html(
				new File(MDToolTest.class.getResource("/fileGBK.md").getFile()), "GBK");
		Assert.assertNotNull(result);
	}

	@Test
	public void testBoldInParagraph() {
		String result = MDTool.markdown2Html("aaa **bold** bbb");
		Assert.assertTrue(result.contains("aaa"));
		Assert.assertTrue(result.contains("<strong>bold</strong>"));
		Assert.assertTrue(result.contains("bbb"));
	}

	@Test
	public void testItalicInParagraph() {
		String result = MDTool.markdown2Html("aaa *italic* bbb");
		Assert.assertTrue(result.contains("aaa"));
		Assert.assertTrue(result.contains("<em>italic</em>"));
		Assert.assertTrue(result.contains("bbb"));
	}

	@Test
	public void testHeaderDoesNotTreatHashAsHeader() {
		String result = MDTool.markdown2Html("not#a header");
		Assert.assertFalse(result.contains("<h"));
	}

	@Test
	public void testSpecialCharactersEscaped() {
		String result = MDTool.markdown2Html("\\\\ backslash");
		Assert.assertFalse(result.contains("$BACKSLASH"));
	}
}

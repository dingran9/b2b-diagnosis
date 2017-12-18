package com.eedu.diagnosis.weixin.util;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * 解析xml字符串 
 * 注意. 根节点为:"xml"
 * @author pc
 *
 */
public class ParaXml {
	private String xmlString;

	private Document document;


	public ParaXml(String xmlString) {
		this.xmlString = xmlString;

		InputSource inputSource = new InputSource(new StringReader(this.xmlString));
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
		} catch (SAXException | IOException | ParserConfigurationException e) {

			e.printStackTrace();
		}

	}

	public String getValue(String key) {

		NodeList root = document.getElementsByTagName("xml");
		 
		Node node = root.item(0); // NodeList中的某一个节点
		NodeList list =node.getChildNodes();
		
		for(int i = 0;i<list.getLength();i++){
			
			String ke = list.item(i).getNodeName();
			if(ke.equals(key)){
				String value =  list.item(i).getTextContent();
				return value;
			}
		}
        
		 return null;

		 
	}

	public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_2640410a32d9]]></ToUserName><FromUserName><![CDATA[o5DzrwVrkV0_z_qv-vDSnz4uNA_8]]></FromUserName><CreateTime>1491559272</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[VIEW]]></Event><EventKey><![CDATA[http://e12baabb.ngrok.io/diagnosis-weixin/oauth2.html]]></EventKey><MenuId>498567633</MenuId></xml>";
		ParaXml paraXml = new ParaXml(xml);

		String va = paraXml.getValue("return_code");
		System.out.println(va);

	}
}

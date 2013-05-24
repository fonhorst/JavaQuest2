package Dict;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import junit.framework.Assert;

import org.junit.Test;

public class ProcessingManagerTest {

	protected String dictAddress = "J:\\Workspace\\Java\\JavaQuest\\�����\\t1\\�������1.txt";
	protected String textAddress = "J:\\Workspace\\Java\\JavaQuest\\�����\\t1\\�����.txt";
	
	@Test
	public void testLineProcessing_1() {
		try {
			ProcessingManager manager = new ProcessingManager();
			manager.LoadDictionary(dictAddress);
			int res = manager.LineProcessing(new AtomicReference<String>("1����_�_������� �������� �����"));
			Assert.assertTrue(res==-1);
			
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLineProcessing_2() {
		try {
			ProcessingManager manager = new ProcessingManager();
			manager.LoadDictionary(dictAddress);
			int res = manager.LineProcessing(new AtomicReference<String>("������. ����_�_������� �������� �����"));
			
			Assert.assertTrue(res == 6);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLineProcessing_3() {
		try {
			ProcessingManager manager = new ProcessingManager();
			manager.LoadDictionary(dictAddress);
			int res = manager.LineProcessing(new AtomicReference<String>("������. ����_�_������� �������� ����� � ���� ������������������� �����, ����������� ����������� ������������ ������. ����_�_������� �������� �����"));
			
			Assert.assertTrue(res == (115 + 3*(manager.getPrefix().length() + manager.getPostfix().length())) );
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLineProcessing_4() {
		try {
			ProcessingManager manager = new ProcessingManager();
			manager.LoadDictionary(dictAddress);
			//����������� ������
			int res = manager.LineProcessing(new AtomicReference<String>("������. ����_�_������� �������� ����� � ���� ������������������� �����, ����������� ����������� ������������ ������.����_�_������� �������� �����"));
			
			Assert.assertTrue(res == 6 );
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLineProcessing_5() {
		try {
			ProcessingManager manager = new ProcessingManager();
			manager.LoadDictionary(dictAddress);
			//����������� ������
			int res = manager.LineProcessing(new AtomicReference<String>("����_�_������� �������� ����� � ���� ������������������� �����. "));
			
			Assert.assertTrue(res == -2 );
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

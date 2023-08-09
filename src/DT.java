import java.util.Scanner;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
class node
{
	node left;
	node right;
	String ques;
	static String strage;
	static int age;
	static String marry;
	static String[][] dataArray = new String[80][5];
	public node(node l, String q, node r)
	{
		left=l;
		ques=q;
		right=r;
	}
	
	//�j�M�H�W
	public static int Search(String SearchName, int count)
	{
		for(int i=0; i<=count; i++) 
		{
			if(dataArray[i][0].equals(SearchName)) 
			{
				strage=dataArray[i][1];
				age=Integer.parseInt(strage);
				marry=dataArray[i][2];
				return i;
			}
		}
		return -1;
	}
	
	
	//�s���ɮק��Ƽg�i�h
	public static void WriteData(int count) throws IOException
	{
		File file = new File("Data.txt");
        FileWriter fw;
		fw = new FileWriter(file,false);
		for(int i=0; i<=count; i++) 
		{
			for(int j=0;j<5; j++) 
			{
				fw.write(dataArray[i][j]+"\n");
			}
		}
        fw.close();
	}
	
	//Ū���ɮ�
	public static int Readfile() throws IOException
	{
		for(int i=0; i<80; i++) 
		{
			for(int j=0;j<5; j++) 
				dataArray[i][j]="";
		}
		FileReader fr = new FileReader("Data.txt");
		Scanner br = new Scanner(fr);
		int count=0;
		for(int i=0; i<80; i++) 
		{
			for(int j=0;j<5; j++) 
			{
				if(br.hasNextLine()) 
					dataArray[i][j]=br.nextLine();
				else
					return count;
			}
			count++;	
		}
		fr.close();
		return count;
	}
	
	//�ק��ɮ�
	public static void EditData(int p, String inputN, String inputA, String inputM)
	{
		dataArray[p][0]=inputN;
		dataArray[p][1]=inputA;
		dataArray[p][2]=inputM;
		dataArray[p][3]="�d�ߵ��G:";
		dataArray[p][4]="";
	}
}	


public class DT 
{
	//�إߨM����
	static node root=null;
	public static void buildDT()
	{
		node temp=root;
		temp=root=new node(null, "�~�֬O�_>=7��", null);
		temp.left=new node(null, "�L�欰��O�H", null);
		temp=temp.right=new node(null, "�~�֬O�_>=20��", null);
		temp.left=new node(null, "�����欰��O�H", null);
		temp=temp.right=new node(null, "�O�_�w���B", null);
		temp.left=new node(null, "�����欰��O�H", null);
		temp.right=new node(null, "����欰��O�H", null);
	}
	
	//�d�ߵ��G
	public static String judge(node temp)
	{
		if(node.age<7)
		{
			temp=temp.left;
		}
		else
		{
			temp=temp.right;
			if(node.age>=20)
			{
				temp=temp.left;
			}
			else
			{
				temp=temp.right;
				if(node.marry.equals("yes"))
				{
					temp=temp.left;
				}
				else
				{
					temp=temp.right;
				}
			}
		}
		return temp.ques;
	}
	
	public static void main(String[] args) throws IOException 
	{
		String name;
		buildDT();
		int datanum=node.Readfile();
		System.out.println("���k�欰��O�d��");
		Scanner sc1 = new Scanner(System.in);
		int n;
		
		while(true) 
		{	
			System.out.print("�аݱz�Q�n1.�̩m�W�d�ߦ欰��O 2.�ק��� 3.�s�W��� 4.���}: ");
			n=sc1.nextInt();
			switch(n) 
			{
			case 1:
				System.out.print("�d�ߩm�W:");
				name=sc1.next();
				int find=node.Search(name, datanum);
				if(name.equals("ALL")) 
				{
					for(int i=0; i<datanum;i++) {
						node.age=Integer.parseInt(node.dataArray[i][1]);
						
						node.marry=node.dataArray[i][2];
						System.out.println(node.dataArray[i][0]+"�O"+judge(root)+"\n");
						node.dataArray[i][3]="�d�ߵ��G:"+judge(root);
					}
				}
				else if(find!=-1) 
				{
					System.out.println(name+"�O"+judge(root)+"\n");
					node.dataArray[find][3]="�d�ߵ��G:"+judge(root);
				}
				else
					System.out.println("NOT FOUND!"+"\n");
				break;
				
			case 2:
				System.out.print("�z�Q�n�ק�֪����:");
				name=sc1.next();
				if(node.Search(name, datanum)==-1) 
				{
					System.out.println("NOT FOUND!"+"\n");
				}
				else 
				{
					System.out.print("�ק�1.�~�� 2.�O�_���B:");
					int in = sc1.nextInt();
					if(in==1) 
					{
						System.out.print("�s���~�֬�: ");
						String newage=sc1.next();
						node.EditData(node.Search(name, datanum), name, newage, node.marry);
					}
					else 
					{
						System.out.print("�s���B�ê��p��: ");
						String newmarry=sc1.next();
						node.EditData(node.Search(name, datanum), name, node.strage, newmarry);	
					}
				}
				System.out.println("�ק��Ʀ��\!\n");
				break;
				
			case 3:
				System.out.print("�s�W�m�W: ");
				String addName=sc1.next();
				System.out.print("�s�W�~��: ");
				String addAge=sc1.next();
				System.out.print("�s�W�B�ê��p: ");
				String addMarry=sc1.next();
				datanum++;
				node.EditData(datanum, addName, addAge, addMarry);
				System.out.println("�s�W��Ʀ��\!\n");
				break;
				
			case 4:
				node.WriteData(datanum);
				return;
				
			default:
				System.out.println("�п�J1~4:");
					
			}
			node.WriteData(datanum);
		}
	}
}

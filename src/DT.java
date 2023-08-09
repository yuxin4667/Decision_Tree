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
	
	//搜尋人名
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
	
	
	//新建檔案把資料寫進去
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
	
	//讀取檔案
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
	
	//修改檔案
	public static void EditData(int p, String inputN, String inputA, String inputM)
	{
		dataArray[p][0]=inputN;
		dataArray[p][1]=inputA;
		dataArray[p][2]=inputM;
		dataArray[p][3]="查詢結果:";
		dataArray[p][4]="";
	}
}	


public class DT 
{
	//建立決策樹
	static node root=null;
	public static void buildDT()
	{
		node temp=root;
		temp=root=new node(null, "年齡是否>=7歲", null);
		temp.left=new node(null, "無行為能力人", null);
		temp=temp.right=new node(null, "年齡是否>=20歲", null);
		temp.left=new node(null, "完全行為能力人", null);
		temp=temp.right=new node(null, "是否已結婚", null);
		temp.left=new node(null, "完全行為能力人", null);
		temp.right=new node(null, "限制行為能力人", null);
	}
	
	//查詢結果
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
		System.out.println("民法行為能力查詢");
		Scanner sc1 = new Scanner(System.in);
		int n;
		
		while(true) 
		{	
			System.out.print("請問您想要1.依姓名查詢行為能力 2.修改資料 3.新增資料 4.離開: ");
			n=sc1.nextInt();
			switch(n) 
			{
			case 1:
				System.out.print("查詢姓名:");
				name=sc1.next();
				int find=node.Search(name, datanum);
				if(name.equals("ALL")) 
				{
					for(int i=0; i<datanum;i++) {
						node.age=Integer.parseInt(node.dataArray[i][1]);
						
						node.marry=node.dataArray[i][2];
						System.out.println(node.dataArray[i][0]+"是"+judge(root)+"\n");
						node.dataArray[i][3]="查詢結果:"+judge(root);
					}
				}
				else if(find!=-1) 
				{
					System.out.println(name+"是"+judge(root)+"\n");
					node.dataArray[find][3]="查詢結果:"+judge(root);
				}
				else
					System.out.println("NOT FOUND!"+"\n");
				break;
				
			case 2:
				System.out.print("您想要修改誰的資料:");
				name=sc1.next();
				if(node.Search(name, datanum)==-1) 
				{
					System.out.println("NOT FOUND!"+"\n");
				}
				else 
				{
					System.out.print("修改1.年齡 2.是否結婚:");
					int in = sc1.nextInt();
					if(in==1) 
					{
						System.out.print("新的年齡為: ");
						String newage=sc1.next();
						node.EditData(node.Search(name, datanum), name, newage, node.marry);
					}
					else 
					{
						System.out.print("新的婚姻狀況為: ");
						String newmarry=sc1.next();
						node.EditData(node.Search(name, datanum), name, node.strage, newmarry);	
					}
				}
				System.out.println("修改資料成功!\n");
				break;
				
			case 3:
				System.out.print("新增姓名: ");
				String addName=sc1.next();
				System.out.print("新增年齡: ");
				String addAge=sc1.next();
				System.out.print("新增婚姻狀況: ");
				String addMarry=sc1.next();
				datanum++;
				node.EditData(datanum, addName, addAge, addMarry);
				System.out.println("新增資料成功!\n");
				break;
				
			case 4:
				node.WriteData(datanum);
				return;
				
			default:
				System.out.println("請輸入1~4:");
					
			}
			node.WriteData(datanum);
		}
	}
}
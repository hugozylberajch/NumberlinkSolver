import java.awt.Color;
import java.util.LinkedList;
public class Main {
	public static void main(String[] args) {
		int[][] test = {{4,0,0,0,0,0,7},{0,0,6,0,3,0,1},{0,0,4,6,7,0,0},{0,0,0,1,0,5,0},{0,3,0,2,0,0,0},{0,0,0,0,0,5,0},{2,0,0,0,0,0,0}};
		int [][] test2 = {{1},{0},{0},{0},{1}};
		int [][] test21 = {{1,0,0,0,1}};
		int[][] test3 = {{0,0,0,0,0,2},{0,0,0,0,0,1},{0,0,0,0,0,3},{0,0,3,0,0,0},{0,0,0,0,2,0},{0,0,0,0,0,1}};
		int[][] test4 = {{1,0,0,4,0},{2,0,0,0,0},{3,0,2,0,0},{0,1,0,0,0},{0,0,0,3,0},{4,0,0,0,0}};
		int[][] test5 = {{1,0,0,2},{0,0,0,0},{0,0,0,0},{1,0,0,2}};
		Numberlink test0 = new Numberlink(7,7,7,test);
		Numberlink test02 = new Numberlink(5,1,1,test2);
		Numberlink test021 = new Numberlink(1,5,1,test21);
		Numberlink test03 = new Numberlink(6,6,3,test3);
		Numberlink test04 = new Numberlink(6,5,4,test4);
		Numberlink test05 = new Numberlink(4,4,2,test5);
		new Image2dViewer(test0.CreateNumberlink());
		//new Image2dViewer(test02.CreateNumberlink());
		//new Image2dViewer(test021.CreateNumberlink());
		//new Image2dViewer(test03.CreateNumberlink());
		//new Image2dViewer(test04.CreateNumberlink());
		//new Image2dViewer(test05.CreateNumberlink());
		int[] flow= {0,0,0,0,0,0,0};
		int[] flow2 = {0};
		int[] flow3 = {0,0,0,0,0};
		int[] flow4 = {0,0,0,0};
		int[] flow5 = {0,0,0};
		System.out.println((test0.NumberlinkSolver(test0.map,test0.LabelEndPosition(),test0.LabelFirstPosition(),0,flow)));
		//System.out.println((test02.NumberlinkSolver(test02.map,test02.LabelEndPosition(),test02.LabelFirstPosition(),0,flow2)));
		//System.out.println((test021.NumberlinkSolver(test021.map,test021.LabelEndPosition(),test021.LabelFirstPosition(),0,flow2)));
		//System.out.println((test03.NumberlinkSolver(test03.map,test03.LabelEndPosition(),test03.LabelFirstPosition(),0,flow5)));
		//System.out.println((test04.NumberlinkSolver(test04.map,test04.LabelEndPosition(),test04.LabelFirstPosition(),0,flow4)));
		//System.out.println((test05.NumberlinkSolver(test05.map,test05.LabelEndPosition(),test05.LabelFirstPosition(),0,flow4)));
		//System.out.println(test05.CountNumberlink());
		//test05.LessThanK(2);
		//test05.LessThanK(7);
	}
}

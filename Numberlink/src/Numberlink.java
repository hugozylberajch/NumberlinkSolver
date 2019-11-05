import java.awt.Color;
import java.util.LinkedList;

public class Numberlink {
	int width;
	int height;
	int labelmax;
	int[][] map;
	static int MAX = Integer.MAX_VALUE;
	
	
	Numberlink(int w, int h,int l, int[][] m) {
		this.width=w;
		this.height=h;
		this.labelmax = l;
		this.map=m;
	}
	
	//Create a Grid
	public static Image2d makequadrillage(int w,int h) {
		Image2d img = new Image2d(256);
		for (int i=0;i<w;i++) {
			for (int j=0; j<h;j++) {
				img.addPolygon(new int[] {50+50*j,50+50*j,50+(j+1)*50,50+(j+1)*50}, new int[] {50+50*i,50+50*(i+1),50+50*(i+1),50+50*i}, Color.WHITE, Color.BLACK);
			}
		}
		return img;
	}
	
	//Create a square
	public static Image2d makesquareat(int xpos,int ypos,Color color) {
		Image2d img = new Image2d(256);
		img.addPolygon(new int[] {50+50*xpos,50+50*xpos,50+50*(xpos+1),50+50*(xpos+1)}, new int[] {50+50*ypos,50+50*(ypos+1),50+50*(ypos+1),50+50*ypos}, color, Color.BLACK);
		return img;
	}
	
	//Create a Numberlink
	Image2d CreateNumberlink() {
		Image2d img = new Image2d(256);
		Color[] couleurs = new Color[11];
		int w = this.width;
		int h = this.height;
		int[][] m = this.map;
		couleurs[0]=Color.WHITE;
		couleurs[1]=Color.BLUE;
		couleurs[2]=Color.CYAN;
		couleurs[3]=Color.GRAY;
		couleurs[4]=Color.GREEN;
		couleurs[5]=Color.MAGENTA;
		couleurs[6]=Color.ORANGE;
		couleurs[7]=Color.RED;
		couleurs[8]=Color.DARK_GRAY;
		couleurs[9]=Color.PINK;
		couleurs[10]=Color.YELLOW;
		for (int i=0;i<w;i++) {
			for (int j=0;j<h;j++) {
				img.add(makesquareat(i,j,couleurs[m[i][j]]));
			}
		}
		return img;
	}
	
	//Initialize the initial conditions
	public int[][] LabelEndPosition(){
		int width = this.width;
		int height = this.height;
		int labelmax = this.labelmax;
		int[][] map = this.map;
		int[][] LabelLastPosition = new int[labelmax][2];
		int[][] LabelEndPosition = new int[labelmax][2];
		int[] LabelVisited = new int[labelmax];
		for (int i=0; i<labelmax;i++) {
			LabelVisited[i] = 2;
		}
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				if (map[i][j]>0 && LabelVisited[map[i][j]-1]>0) {
					if (LabelVisited[map[i][j]-1]==2) {
						LabelLastPosition[map[i][j]-1][0]=i ; 
						LabelLastPosition[map[i][j]-1][1]=j ; 
						LabelVisited[map[i][j]-1]-=1;
					}
					else {
						LabelEndPosition[map[i][j]-1][0]=i;
						LabelEndPosition[map[i][j]-1][1]=j;
					}
				}
			}
		}
		System.out.println(LabelEndPosition[0][0]);
		System.out.println(LabelEndPosition[0][1]);
		return LabelEndPosition;
	}
	//Initialize the final positions
	public int[][] LabelFirstPosition(){
		int width = this.width;
		int height = this.height;
		int labelmax = this.labelmax;
		int[][] map = this.map;
		int[][] LabelLastPosition = new int[labelmax][2];
		int[][] LabelEndPosition = new int[labelmax][2];
		int[] LabelVisited = new int[labelmax];
		for (int i=0; i<labelmax;i++) {
			LabelVisited[i] = 2;
		}
		for (int i=0;i<width;i++) {
			for (int j=0;j<height;j++) {
				if (map[i][j]>0 && LabelVisited[map[i][j]-1]>0) {
					if (LabelVisited[map[i][j]-1]==2) {
						LabelLastPosition[map[i][j]-1][0]=i ; 
						LabelLastPosition[map[i][j]-1][1]=j ; 
						LabelVisited[map[i][j]-1]-=1;
					}
					else {
						LabelEndPosition[map[i][j]-1][0]=i;
						LabelEndPosition[map[i][j]-1][1]=j;
					}
				}
			}
		}
		System.out.println(LabelLastPosition);
		return LabelLastPosition;
	}
	
	//Solves Numberlink
	boolean NumberlinkSolver(int[][] map, int[][] LabelLastPosition, int[][] LabelEndPosition, int SolveCounter, int[] FlowDone){
		long t0 = System.currentTimeMillis();
		//RESOLU
		if (SolveCounter == this.labelmax && !HaveZeros(map)) {
			Numberlink solved = new Numberlink(this.width,this.height,this.labelmax,map);
			new Image2dViewer(solved.CreateNumberlink());
			long tf = System.currentTimeMillis();
			System.out.println("Computation time : " + (tf - t0) + " ms");
			return true;
		}
		Color[] couleurs = new Color[11];
		couleurs[0]=Color.WHITE;
		couleurs[1]=Color.BLUE;
		couleurs[2]=Color.CYAN;
		couleurs[3]=Color.GRAY;
		couleurs[4]=Color.GREEN;
		couleurs[5]=Color.MAGENTA;
		couleurs[6]=Color.ORANGE;
		couleurs[7]=Color.RED;
		couleurs[8]=Color.ORANGE;
		couleurs[9]=Color.PINK;
		couleurs[10]=Color.YELLOW;
		int width = this.width;
		int height = this.height;
		int labelmax = this.labelmax;
		
		// See auxilary function 
		int[][] VoisinsDisponibles = new int[labelmax][4];
		int[] nombrevoisinslibres= new int[labelmax];
		for (int i=0; i < labelmax ; i++) {
			for (int j=0;j<4;j++) {
				if (voisinestlibre(i,LabelLastPosition[i][0],LabelLastPosition[i][1],j,width,height,map,VoisinsDisponibles)) {
					nombrevoisinslibres[i]+=1;
				}
			}
		}
		for (int i=0; i<labelmax; i++) {
			if (FlowDone[i]==1) {
				nombrevoisinslibres[i]=MAX;
			}
		}
		//Index with least neighbourhs
		int ind = IndexOfSmallest(nombrevoisinslibres);
		SolveCounter = 0;
		//left
		if (VoisinsDisponibles[ind][0]==-1) {
			LabelLastPosition[ind][0]-=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++){
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			//BACKTRACKING
			if (this.NumberlinkSolver(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone)) {
				return true;
			}
			else {
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][0]+=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//down
		if (VoisinsDisponibles[ind][1]==-1 ) {
			LabelLastPosition[ind][1]+=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			//BACKTRACKING
			if (this.NumberlinkSolver(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone)) {
				return true;
			}
			else {
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][1]-=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//right
		if (VoisinsDisponibles[ind][2]==-1 ) {
			LabelLastPosition[ind][0]+=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			//BACKTRACKING
			if (this.NumberlinkSolver(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone)) {
				return true;
			}
			else {
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][0]-=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//up
		if (VoisinsDisponibles[ind][3]==-1 ) {
			LabelLastPosition[ind][1]-=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			//BACKTRACKING
			if (this.NumberlinkSolver(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone)) {
				return true;
			}
			else {
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][1]+=1;
				FlowDone[ind]=0;
			}
		}
		
		return false;
	}

	//0 --> left
	//1 --> down
	//2 --> right
	//3 --> left up
	public static boolean voisinestlibre(int label,int xpos ,int ypos, int dir, int width, int height, int[][] map, int[][] VoisinsDisponibles) {
		if (dir==0) {
			if (xpos==0) {
				return false;
			}
			else {
				if (map[xpos-1][ypos]==0) {
					VoisinsDisponibles[label][0]=-1;
					return true;
				}
				else {
					VoisinsDisponibles[label][0]=map[xpos-1][ypos];
					return false;
				}
			}
		}
		if (dir==1) {
			if (ypos==height-1) {
				return false;
			}
			else {
				if (map[xpos][ypos+1]==0) {
					VoisinsDisponibles[label][1]=-1;
					return true;
				}
				else {
					VoisinsDisponibles[label][1]=map[xpos][ypos+1];
					return false;
				}
			}
		}
		if (dir==2) {
			if (xpos==width-1) {
				return false;
			}
			else {
				if (map[xpos+1][ypos]==0) {
					VoisinsDisponibles[label][2]=-1;
					return true;
				}
				else {
					VoisinsDisponibles[label][2]=map[xpos+1][ypos];
					return false;
				}
			}
		}
		if (dir==3) {
			if (ypos==0) {
				return false;
			}
			else {
				if (map[xpos][ypos-1]==0) {
					VoisinsDisponibles[label][3]=-1;
					return true;
				}
				else {
					VoisinsDisponibles[label][3]=map[xpos][ypos-1];
					return false;
				}
			}
		}
		return false;
	}
	
	//check is the lebel's flow is done
	public boolean CheckSolved(int label,int[][] LabelEndPosition, int[][] LabelLastPosition) {
		int width = this.width;
		int height = this.height;
		if (LabelLastPosition[label][0]-1>=0 && LabelLastPosition[label][0]-1 == LabelEndPosition[label][0] ) {
			if (LabelLastPosition[label][1] == LabelEndPosition[label][1] ) {
				return true;
			}
		}
		if (LabelLastPosition[label][0]+1<height && LabelLastPosition[label][0]+1 == LabelEndPosition[label][0] ) {
			if (LabelLastPosition[label][1] == LabelEndPosition[label][1] ) {
				return true;
			}
		}
		if (LabelLastPosition[label][1]-1>=0 && LabelLastPosition[label][1]-1 == LabelEndPosition[label][1] ) {
			if (LabelLastPosition[label][0] == LabelEndPosition[label][0] ) {
				return true;
			}
		}
		if (LabelLastPosition[label][1]+1<width && LabelLastPosition[label][1]+1 == LabelEndPosition[label][1] ) {
			if (LabelLastPosition[label][0] == LabelEndPosition[label][0] ) {
				return true;
			}
		}
		return false;
	}
	
	public static int IndexOfSmallest(int[] a) {
		int b=a[0];
		int c = 0;
		for (int i=1;i<a.length;i++) {
			if (a[i]<b) {
				b=a[i];
				c = i;
			}
		}
		return c;
	}
	
	
	//Solve all posible solutions of Numberlink
	boolean NumberlinkInstance(int[][] map, int[][] LabelLastPosition, int[][] LabelEndPosition, int SolveCounter, int[] FlowDone,LinkedList<int[][]>  mapcheck){
		if (SolveCounter == this.labelmax && !HaveZeros(map) && !AllEquals(map,mapcheck)) {
			Numberlink solved = new Numberlink(this.width,this.height,this.labelmax,map);
			new Image2dViewer(solved.CreateNumberlink());
			return true;
		}
		Color[] couleurs = new Color[11];
		couleurs[0]=Color.WHITE;
		couleurs[1]=Color.BLUE;
		couleurs[2]=Color.CYAN;
		couleurs[3]=Color.GRAY;
		couleurs[4]=Color.GREEN;
		couleurs[5]=Color.MAGENTA;
		couleurs[6]=Color.ORANGE;
		couleurs[7]=Color.RED;
		couleurs[8]=Color.ORANGE;
		couleurs[9]=Color.PINK;
		couleurs[10]=Color.YELLOW;
		int width = this.width;
		int height = this.height;
		int labelmax = this.labelmax;
		
		
		int[][] VoisinsDisponibles = new int[labelmax][4];
		int[] nombrevoisinslibres= new int[labelmax];
		for (int i=0; i < labelmax ; i++) {
			for (int j=0;j<4;j++) {
				if (voisinestlibre(i,LabelLastPosition[i][0],LabelLastPosition[i][1],j,width,height,map,VoisinsDisponibles)) {
					nombrevoisinslibres[i]+=1;
				}
			}
		}
		for (int i=0; i<labelmax; i++) {
			if (FlowDone[i]==1) {
				nombrevoisinslibres[i]=MAX;
			}
		}
		int ind = IndexOfSmallest(nombrevoisinslibres);
		SolveCounter = 0;
		//left
		if (VoisinsDisponibles[ind][0]==-1) {
			LabelLastPosition[ind][0]-=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++){
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone, mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][0]+=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//down
		if (VoisinsDisponibles[ind][1]==-1 ) {
			LabelLastPosition[ind][1]+=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone, mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][1]-=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//right
		if (VoisinsDisponibles[ind][2]==-1 ) {
			LabelLastPosition[ind][0]+=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone, mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][0]-=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//up
		if (VoisinsDisponibles[ind][3]==-1 ) {
			LabelLastPosition[ind][1]-=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone,mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][1]+=1;
				FlowDone[ind]=0;
			}
		}
		return false;
	}
	
	//Instance of Numberlink randomized search
	boolean RandomNumberlinkInstance(int[][] map, int[][] LabelLastPosition, int[][] LabelEndPosition, int SolveCounter, int[] FlowDone,LinkedList<int[][]>  mapcheck){
		if (SolveCounter == this.labelmax && !HaveZeros(map) && !AllEquals(map,mapcheck)) {
			Numberlink solved = new Numberlink(this.width,this.height,this.labelmax,map);
			new Image2dViewer(solved.CreateNumberlink());
			return true;
		}
		Color[] couleurs = new Color[11];
		couleurs[0]=Color.WHITE;
		couleurs[1]=Color.BLUE;
		couleurs[2]=Color.CYAN;
		couleurs[3]=Color.GRAY;
		couleurs[4]=Color.GREEN;
		couleurs[5]=Color.MAGENTA;
		couleurs[6]=Color.ORANGE;
		couleurs[7]=Color.RED;
		couleurs[8]=Color.ORANGE;
		couleurs[9]=Color.PINK;
		couleurs[10]=Color.YELLOW;
		int width = this.width;
		int height = this.height;
		int labelmax = this.labelmax;
		
		
		int[][] VoisinsDisponibles = new int[labelmax][4];
		int[] nombrevoisinslibres= new int[labelmax];
		for (int i=0; i < labelmax ; i++) {
			for (int j=0;j<4;j++) {
				if (voisinestlibre(i,LabelLastPosition[i][0],LabelLastPosition[i][1],j,width,height,map,VoisinsDisponibles)) {
					nombrevoisinslibres[i]+=1;
				}
			}
		}
		for (int i=0; i<labelmax; i++) {
			if (FlowDone[i]==1) {
				nombrevoisinslibres[i]=MAX;
			}
		}
		//Random label 
		int ind = (int)( Math.random()*( labelmax )) ;
		SolveCounter = 0;
		//gauche
		if (VoisinsDisponibles[ind][0]==-1) {
			LabelLastPosition[ind][0]-=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++){
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone, mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][0]+=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//bas
		if (VoisinsDisponibles[ind][1]==-1 ) {
			LabelLastPosition[ind][1]+=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone, mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][1]-=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//droite
		if (VoisinsDisponibles[ind][2]==-1 ) {
			LabelLastPosition[ind][0]+=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone, mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][0]-=1;
				FlowDone[ind]=0;
			}
		}
		SolveCounter = 0;
		//bas
		if (VoisinsDisponibles[ind][3]==-1 ) {
			LabelLastPosition[ind][1]-=1;
			map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=ind+1;
			for (int i=0; i<labelmax; i++) {
				if (this.CheckSolved(i,LabelEndPosition,LabelLastPosition)) {
					SolveCounter+=1;
				}
			}
			if (this.CheckSolved(ind, LabelEndPosition, LabelLastPosition)) {
				FlowDone[ind]=1;
			}
			
			if (this.NumberlinkInstance(map, LabelLastPosition, LabelEndPosition, SolveCounter,FlowDone,mapcheck)) {
				return true;
			}
			else {
				
				map[LabelLastPosition[ind][0]][LabelLastPosition[ind][1]]=0;
				LabelLastPosition[ind][1]+=1;
				FlowDone[ind]=0;
			}
		}
		return false;
	}
	
	//Number of solutions
	int RandomCountNumberlink() {
		int Counter = 0;
		int labelmax = this.labelmax;
		int[][] map = this.map;
		int[][] map1 = new int[this.width][this.height];
		int[][] map2 = new int[this.width][this.height];
		//On stocke les résultats 
		LinkedList<int[][]> listmap = new LinkedList<>();
		CopyMaps(map1,map);
		CopyMaps(map2,map);
		int[] flow = new int[labelmax];
		long t0 = System.currentTimeMillis();
		System.out.println(this.NumberlinkSolver(map1, this.LabelFirstPosition(), this.LabelEndPosition(), 0, flow));
		Counter	+= 1;
		flow = new int[labelmax];
		listmap.add(map1);
		
		while (this.NumberlinkInstance(map2, this.LabelFirstPosition(), this.LabelEndPosition(),0, flow, listmap)) {
			listmap.add(map2);
			map1 = map2;
			map2 = new int[this.width][this.height];
			CopyMaps(map2,this.map);
			flow = new int[labelmax];
			Counter+=1;
			System.out.println(Counter);
		}
		
		map = this.map;
		map1 = new int[this.width][this.height];
		map2 = new int[this.width][this.height];
		CopyMaps(map1,map);
		CopyMaps(map2,map);
		flow = new int[labelmax];
		
		while (this.NumberlinkInstance(map2, this.LabelEndPosition(), this.LabelFirstPosition(),0, flow, listmap)) {
			listmap.add(map2);
			map1 = map2;
			map2 = new int[this.width][this.height];
			CopyMaps(map2,this.map);
			flow = new int[labelmax];
			Counter+=1;
			System.out.println(Counter);
		}
		
		long tf = System.currentTimeMillis();
		System.out.println("Computation time : " + (tf - t0) + " ms");
		return Counter;
	}
	
	
	//Return list of all solutions
	LinkedList<int[][]> CountNumberlink2(int Counter) {
		int labelmax = this.labelmax;
		int[][] map = this.map;
		int[][] map1 = new int[this.width][this.height];
		int[][] map2 = new int[this.width][this.height];
		//On stocke les résultats 
		LinkedList<int[][]> listmap = new LinkedList<>();
		CopyMaps(map1,map);
		CopyMaps(map2,map);
		int[] flow = new int[labelmax];
		long t0 = System.currentTimeMillis();
		System.out.println(this.NumberlinkSolver(map1, this.LabelFirstPosition(), this.LabelEndPosition(), 0, flow));
		Counter	+= 1;
		flow = new int[labelmax];
		listmap.add(map1);
		
		while (this.NumberlinkInstance(map2, this.LabelFirstPosition(), this.LabelEndPosition(),0, flow, listmap)) {
			listmap.add(map2);
			map1 = map2;
			map2 = new int[this.width][this.height];
			CopyMaps(map2,this.map);
			flow = new int[labelmax];
			Counter+=1;
		}
		
		map = this.map;
		map1 = new int[this.width][this.height];
		map2 = new int[this.width][this.height];
		CopyMaps(map1,map);
		CopyMaps(map2,map);
		flow = new int[labelmax];
		
		while (this.NumberlinkInstance(map2, this.LabelEndPosition(), this.LabelFirstPosition(),0, flow, listmap)) {
			listmap.add(map2);
			map1 = map2;
			map2 = new int[this.width][this.height];
			CopyMaps(map2,this.map);
			flow = new int[labelmax];
			Counter+=1;
		}
		
		long tf = System.currentTimeMillis();
		System.out.println("Computation time : " + (tf - t0) + " ms");
		return listmap;
	}
	
	public void LessThanK(int k) {
		LinkedList<int[][]> listmap = this.CountNumberlink2(0);
		if (listmap.size()<k) {
			new Image2dViewer(this.CreateNumberlink());
			return;
		}
		else {
			int[][] m = listmap.getFirst();
			Numberlink n = new Numberlink(this.width,this.height,this.labelmax,m);
			new Image2dViewer(n.CreateNumberlink());
			return;
		}
	}
	
	//Copy matrix
	public static void CopyMaps(int[][] map1, int[][] map2) {
		int rows = map1.length;
		int cols = map1[0].length;
		for (int i = 0; i<rows;i++) {
			for (int j =0; j< cols; j++) {
				map1[i][j]=map2[i][j] ;
			}
		}
	}
	
	//Check if two matrix are equal
	public static boolean EqualsMaps(int[][] map1, int[][] map2) {
		int rows = map1.length;
		int cols = map1[0].length;
		for (int i = 0; i<rows;i++) {
			for (int j =0; j< cols; j++) {
				if (map1[i][j]!=map2[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean AllEquals(int[][] map, LinkedList<int[][]> listmap ) {
		int n = listmap.size();
		for  (int i=0;i<n;i++) {
			if (EqualsMaps(map,listmap.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	//Gave Zeros
	public static boolean HaveZeros(int[][] map1) {
		int rows = map1.length;
		int cols = map1[0].length;
		for (int i = 0; i<rows;i++) {
			for (int j =0; j< cols; j++) {
				if (map1[i][j]==0) {
					return true;
				}
			}
		}
		return false;
	}
	
}

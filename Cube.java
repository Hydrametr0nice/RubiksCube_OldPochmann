package rubikscube_oldpochmann;

import java.util.ArrayList;
import java.util.Arrays;

public class Cube {

	private final Sticker[][] stickers;
	private final ArrayList<Vertex> vertices;
	private final ArrayList<Edge> edges;
	private String[] solution;
	private boolean rPerm;
	
	public Cube(String _input) {
		this.stickers = new Sticker[6][9];		
		this.vertices = new ArrayList<Vertex>();
		this.edges =  new ArrayList<Edge>();
		this.solution = new String[2];
		for(int i = 0; i < 2; i++)
			solution[i] = new String();
		this.rPerm = false;
		
		for(int f = 0; f < 6; f++)
			for(int p = 0; p < 9; p++)
				stickers[f][p] = new Sticker(f, p);
		
		int[] input = toInteger(_input);
		System.out.println(Arrays.toString(input));
				
		setColors(input);
		
		setAllAdjacents();
		System.out.println(Arrays.toString(vertices.toArray()));
		System.out.println(Arrays.toString(edges.toArray()));
		
		solveVertices();
				
		solveEdges();
		
		printSolution();
		
		checkSolution();
		
		printSolution();
	}
	
	private int[] toInteger(String _input) {
		char[] colorScheme = new char[6];
		for(int i = 0; i < 6; i++)
			colorScheme[i] = _input.charAt(i * 9 + 4);
		
		int[] input = new int[54];
		
		for(int i = 0; i < 54; i++)
			if(_input.charAt(i) == colorScheme[0])
				input[i] = 0;
			else if(_input.charAt(i) == colorScheme[1])
				input[i] = 1;
			else if(_input.charAt(i) == colorScheme[2])
				input[i] = 2;
			else if(_input.charAt(i) == colorScheme[3])
				input[i] = 3;
			else if(_input.charAt(i) == colorScheme[4])
				input[i] = 4;
			else if(_input.charAt(i) == colorScheme[5])
				input[i] = 5;
		
		return input;
	}

	private void setColors(int[] input)
	{
		for(int f = 0; f < 6; f++)
			for(int p = 0; p < 9; p++)
				this.stickers[f][p].setColor(input[f * 9 + p]);
	}

	private void setAllAdjacents() {
		edges.add(new Edge(this.stickers[0][5], this.stickers[2][1]));
		edges.add(new Edge(this.stickers[0][1], this.stickers[3][1]));
		edges.add(new Edge(this.stickers[0][3], this.stickers[4][1]));
		edges.add(new Edge(this.stickers[0][7], this.stickers[1][1]));
		edges.add(new Edge(this.stickers[5][1], this.stickers[1][7]));
		edges.add(new Edge(this.stickers[5][3], this.stickers[4][7]));
		edges.add(new Edge(this.stickers[5][5], this.stickers[2][7]));
		edges.add(new Edge(this.stickers[5][7], this.stickers[3][7]));
		edges.add(new Edge(this.stickers[1][5], this.stickers[2][3]));
		edges.add(new Edge(this.stickers[2][5], this.stickers[3][3]));
		edges.add(new Edge(this.stickers[3][5], this.stickers[4][3]));
		edges.add(new Edge(this.stickers[4][5], this.stickers[1][3]));
		
		vertices.add(new Vertex(this.stickers[0][0], this.stickers[3][2], this.stickers[4][0]));
		vertices.add(new Vertex(this.stickers[0][2], this.stickers[2][2], this.stickers[3][0]));
		vertices.add(new Vertex(this.stickers[0][6], this.stickers[4][2], this.stickers[1][0]));
		vertices.add(new Vertex(this.stickers[0][8], this.stickers[1][2], this.stickers[2][0]));
		vertices.add(new Vertex(this.stickers[5][0], this.stickers[1][6], this.stickers[4][8]));
		vertices.add(new Vertex(this.stickers[5][2], this.stickers[2][6], this.stickers[1][8]));
		vertices.add(new Vertex(this.stickers[5][6], this.stickers[4][6], this.stickers[3][8]));
		vertices.add(new Vertex(this.stickers[5][8], this.stickers[3][6], this.stickers[2][8]));
	}

	private void solveVertices() {	
		int orientation = 0;
		Vertex auxVertex;
		Vertex prevVertex;
		
		while(!this.vertices.isEmpty())
		{
			auxVertex = this.vertices.get(0);
			this.solution[0] += auxVertex.getStickers()[0].getLetter();
			orientation = auxVertex.getStickers()[0].getColor();
			
			do
			{
				prevVertex = auxVertex;

				auxVertex = searchNextVertex(auxVertex);
				
				this.vertices.remove(auxVertex);
				
				if(auxVertex != null)
					for(Sticker sticker : auxVertex.getStickers())
						if(sticker.getFace() == orientation)
						{
							this.solution[0] += sticker.getLetter();
							orientation = sticker.getColor();
							break;
						}
			} while(auxVertex != prevVertex && auxVertex != null);
		}
	}
	
	private Vertex searchNextVertex(Vertex vertex) {
		int[] buffer = new int[6];
		boolean found;
		
		for(Vertex v : this.vertices)
		{
			Arrays.fill(buffer, 0);
			found = true;
			for(Sticker sticker : vertex.getStickers())
				buffer[sticker.getColor()]++;
			
			for(Sticker sticker : v.getStickers())
				buffer[sticker.getFace()]--;
			
			for(int i : buffer)
				if(i != 0)
				{
					found = false;
					break;
				}
			
			if(found)
				return v;
		}
		
		return null;
	}
	
	private void solveEdges() {	
		int orientation = 0;
		Edge prevEdge;
		
		while(!this.edges.isEmpty())
		{
			Edge auxEdge = this.edges.get(0);
			this.solution[1] += auxEdge.getStickers()[0].getLetter();
			orientation = auxEdge.getStickers()[0].getColor();
			
			do
			{
				prevEdge = auxEdge;

				auxEdge = searchNextEdge(auxEdge);
				
				this.edges.remove(auxEdge);
				
				if(auxEdge != null)
					for(Sticker sticker : auxEdge.getStickers())
						if(sticker.getFace() == orientation)
						{
							this.solution[1] += sticker.getLetter();
							orientation = sticker.getColor();
							break;
						}
			} while(auxEdge != prevEdge && auxEdge != null);
		}
	}
	
	private Edge searchNextEdge(Edge edge) {
		int[] buffer = new int[6];
		boolean found;
		
		for(Edge e : this.edges)
		{
			Arrays.fill(buffer, 0);
			found = true;
			for(Sticker sticker : edge.getStickers())
				buffer[sticker.getColor()]++;
			
			for(Sticker sticker : e.getStickers())
				buffer[sticker.getFace()]--;
			
			for(int i : buffer)
				if(i != 0)
				{
					found = false;
					break;
				}
			
			if(found)
				return e;
		}
			
		return null;
	}

	private void checkSolution() {
		this.solution[0] = this.solution[0].replace("A", "");
		this.solution[0] = this.solution[0].replace("N", "");
		this.solution[0] = this.solution[0].replace("Q", "");
		this.solution[1] = this.solution[1].replace("b", "");
		this.solution[1] = this.solution[1].replace("i", "");
		
		for(int i = 0; i < 2; i++)
			for(int c = 0; c < this.solution[i].length() - 1;)
			{
				if(this.solution[i].charAt(c) == this.solution[i].charAt(c + 1))
					this.solution[i] = this.solution[i].replace(Character.toString(this.solution[i].charAt(c)), "");
				else
					c++;
			}
		
		if(this.solution[0].length() % 2 == 1)
			this.rPerm = true;
	}
	
	private void printSolution() {
		System.out.print(this.solution[0] + " ");
		if(this.rPerm)
			System.out.print("rPerm ");
		System.out.println(this.solution[1]);
	}
}
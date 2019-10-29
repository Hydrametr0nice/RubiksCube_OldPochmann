package rubikscube_oldpochmann;

public class Edge {

	private Sticker[] stickers;
	
	public Edge(Sticker sticker1, Sticker sticker2) {
		this.stickers = new Sticker[] {sticker1, sticker2};
	}

	public Sticker[] getStickers() {
		return stickers;
	}
}

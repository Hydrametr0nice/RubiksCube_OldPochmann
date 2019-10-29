package rubikscube_oldpochmann;

public class Vertex {

	private Sticker[] stickers;
	
	public Vertex(Sticker sticker1, Sticker sticker2, Sticker sticker3) {
		this.stickers = new Sticker[] {sticker1, sticker2, sticker3};
	}
	
	public Sticker[] getStickers() {
		return stickers;
	}
}

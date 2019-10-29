package rubikscube_oldpochmann;

public class Sticker {
	
	private int face; //face do sticker
	private int position; //posicao do sticker
	private int color; //cor do sticker
	private char letter; //letra da posicao do sticker
		
	public Sticker(int face, int position) {
		this.face = face;
		this.position = position;
		
		if(position == 4)
			this.letter = '\0';
		else if(position % 2 == 0)
			if(position == 8)
				this.letter = (char)('A' + face * 4 + 2);
			else
				this.letter = (char)('A' + face * 4 + position / 2);
		else
		{
			if(position == 1)
				this.letter = (char)('a' + face * 4);
			else if(position == 3)
				this.letter = (char)('a' + face * 4 + 3);
			else
				this.letter = (char)('a' + face * 4 + (position - 3) / 2);
		}
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	public int getFace() {
		return face;
	}

	public int getPosition() {
		return position;
	}

	public int getColor() {
		return color;
	}

	public char getLetter() {
		return letter;
	}
}

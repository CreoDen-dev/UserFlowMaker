package userflowmaker;

public class Link {
	
	public Node a;
	public Node b;
	public boolean direct;
	
	public Link() {
		this(null, null, false);
	}
	
	public Link(Node a, Node b) {
		this(a, b, false);
	}
	
	public Link(Node a, Node b, boolean direct) {
		this.a = a;
		this.b = b;
		this.direct = direct;
	}
}

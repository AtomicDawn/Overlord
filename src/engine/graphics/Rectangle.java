package engine.graphics;

import engine.util.Vector2f;
import engine.util.Vector3f;

public class Rectangle {
    private Mesh mesh;

    public Rectangle(Vertex[] vertices, int[] indices) {
        if (vertices.length != 4) {
            System.err.println("Rectangles require 4 verts, you sent " + vertices.length);
            return;
        }

        if (indices.length != 6) {
            System.err.println("Rectangles require 6 indices, you sent " + indices.length);
            return;
        }

        mesh = new Mesh(vertices, indices, true);
    }

    public Rectangle(int w, int l) {
        Vertex tl = new Vertex(new Vector3f(0, 0, 0), new Vector2f(0, 0));
        Vertex bl = new Vertex(new Vector3f(0, 0, l), new Vector2f(0, 1));
        Vertex tr = new Vertex(new Vector3f(w, 0, 0), new Vector2f(1, 0));
        Vertex br = new Vertex(new Vector3f(w, 0, l), new Vector2f(1, 1));

        int[] indices = new int[]{0, 1, 2, 2, 1, 3};

        mesh = new Mesh(new Vertex[]{tl, bl, tr, br}, indices);
    }

    public void draw() {
        mesh.draw();
    }
}

package engine.graphics;

public class Triangle {
    private Mesh mesh;

    public Triangle(Vertex[] vertices, int[] indices) {
        if (vertices.length != 3) {
            System.err.println("Triangles require 3 verts, you sent " + vertices.length);
            return;
        }

        if (indices.length != 3) {
            System.err.println("Triangles require 3 indices, you sent " + indices.length);
            return;
        }

        mesh = new Mesh(vertices, indices, true);
    }

    public void draw() {
        mesh.draw();
    }
}

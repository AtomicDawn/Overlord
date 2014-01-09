import org.newdawn.slick.opengl.TextureLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

public class ResourceLoader {
    private static final ResourceLoader INSTANCE = new ResourceLoader();

    private ResourceLoader() {
    }

    public static ResourceLoader getInstance() {
        return INSTANCE;
    }

    public String loadFile(String filename) {
        return null;
    }

    public Texture loadTexture(String filename) {
        String[] splitArray = filename.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        try {
            int id = TextureLoader
                    .getTexture(
                            ext,
                            new FileInputStream(new File("./res/textures/"
                                    + filename))).getTextureID();

            return new Texture(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public Mesh loadMesh(String filename) {
        Mesh mesh = null;
        String[] splitArray = filename.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if (!ext.equals("obj")) {
            System.err
                    .println("Error: File format not supported for mesh data: "
                            + ext);
            new Exception().printStackTrace();
            System.exit(1);
        }

        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Integer> indices = new ArrayList<Integer>();

        BufferedReader meshReader = null;

        try {
            meshReader = new BufferedReader(new FileReader("./res/models/"
                    + filename));
            String line;

            while ((line = meshReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyStrings(tokens);

                if (tokens.length == 0 || tokens[0].equals("#"))
                    continue;
                else if (tokens[0].equals("v")) {
                    vertices.add(new Vertex(new Vector3f(Float
                            .valueOf(tokens[1]), Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3]))));
                } else if (tokens[0].equals("f")) {
                    indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);

                    if (tokens.length > 4) {
                        indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
                    }
                }
            }

            meshReader.close();

            Vertex[] vertexData = new Vertex[vertices.size()];
            vertices.toArray(vertexData);

            Integer[] indexData = new Integer[indices.size()];
            indices.toArray(indexData);
            System.out.println(indices.size() + " " + indices.size() / 3);
            mesh = new Mesh(vertexData, Util.toIntArray(indexData), true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return mesh;
    }
}

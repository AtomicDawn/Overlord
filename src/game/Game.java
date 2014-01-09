package game;

import engine.graphics.*;
import engine.util.*;

public class Game {
    private Mesh zedTest;
    private Rectangle rect;
    private Shader shader;
    private Material material, zedMat;
    private Transform transform, zedTrans;
    private Camera camera;

    PointLight pLight1 = new PointLight(new BaseLight(new Vector3f(1, 1f, 0),
            1.0f), new Attenuation(0, 0, 0.2f), new Vector3f(-2, 0, 5f), 10);
    PointLight pLight2 = new PointLight(new BaseLight(new Vector3f(0, 2f, 1),
            1.0f), new Attenuation(0, 0, 0.2f), new Vector3f(2, 0.1f, 3.911f), 10);

    SpotLight sLight1 = new SpotLight(new PointLight(new BaseLight(
            new Vector3f(0, 1f, 0f), 0.8f), new Attenuation(0, 0, 0.1f),
            new Vector3f(-2, 0, 5f), 30), new Vector3f(1, 1, 1), 0.7f);

    public Game() {// engine.util.ResourceLoader.loadMesh("box.obj");
        material = new Material(ResourceLoader.getInstance().loadTexture("test.png"), new Vector3f(1, 1, 1),
                1, 8);
        zedMat = new Material(ResourceLoader.getInstance().loadTexture("test.png"), new Vector3f(1, 1, 1),
                0.2f, 20);

        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;

        Vertex[] vertices = new Vertex[]{
                new Vertex(new Vector3f(-5, 0.0f, -5),
                        new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-5, 0.0f, 5 * 3),
                        new Vector2f(0.0f, 1.0f)),
                new Vertex(new Vector3f(5 * 3, 0.0f, -5),
                        new Vector2f(1.0f, 0.0f)),
                new Vertex(new Vector3f(5 * 3, 0.0f, 5 * 3),
                        new Vector2f(1.0f, 1.0f))
        };

        int indices[] = {0, 1, 2, 2, 1, 3};

        rect = new Rectangle(vertices, indices);
        zedTest = ResourceLoader.getInstance().loadMesh("cube.obj");
        shader = PhongShader.getInstance();
        camera = new Camera();
        transform = new Transform();
        zedTrans = new Transform();


        Transform.setProjection(70f, Window.getWidth(), Window.getHeight(),
                0.1f, 1000);
        Transform.setCamera(camera);

        PhongShader.setAmbientLight(new Vector3f(0.5f, 0.5f, 0.5f));
        PhongShader.setDirectionalLight(new DirectionalLight(new BaseLight(
                new Vector3f(1, 1, 1), 0.1f), new Vector3f(1, 1, 1)));

        PhongShader.setPointLights(new PointLight[]{pLight1, pLight2});
        PhongShader.setSpotLights(new SpotLight[]{sLight1});
    }

    public void input() {
        camera.input();
    }

    float temp = 0.0f;

    public void update() {
        temp += Time.getDelta();

        float sinTemp = (float) Math.sin(temp);

        transform.setTranslation(0, -1, 5);
        //transform.setRotation(0, sinTemp * 180, 0);

        pLight1.setPosition(new Vector3f(7, 0, -8.0f * (float) (Math.cos(temp) + 1.0 / 2.0) + 10));
        pLight2.setPosition(new Vector3f(7, 0,
                8.0f * (float) (Math.cos(temp) + 1.0 / 2.0) + 10));

        // transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
        sLight1.getPointLight().setPosition(camera.getPos());
        sLight1.setDirection(camera.getForward());
    }

    public void render() {
        RenderUtil.setClearColor(Transform.getCamera().getPos().div(2048f)
                .abs());
        shader.bind();
        shader.updateUniforms(transform.getTransformation(),
                transform.getProjectedTransformation(), material);
        rect.draw();
        shader.updateUniforms(zedTrans.getTransformation(),
                zedTrans.getProjectedTransformation(), zedMat);
        zedTest.draw();
    }
}

//package fx;
//
//import com.jogamp.common.nio.Buffers;
//import java.nio.ByteBuffer;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.image.ImageView;
//import javafx.scene.image.PixelFormat;
//import javafx.scene.image.WritableImage;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import javafx.stage.WindowEvent;
//import javax.media.opengl.GL2;
//import javax.media.opengl.GLAutoDrawable;
//import javax.media.opengl.GLCapabilities;
//import javax.media.opengl.GLEventListener;
//import javax.media.opengl.GLProfile;
//import javax.media.opengl.awt.GLCanvas;
//import javax.swing.JFrame;
//
//public class Todel extends Application {
//
//    static int width = 512;
//    static int height = 480;
//    static ByteBuffer byteBuffer;
//
//    @Override
//    public void start(Stage primaryStage) {
//        final WritableImage writableImage = new WritableImage(640, 480);
////        final PixelFormat<ByteBuffer> pf = PixelFormat.getByteBgraPreInstance();
//        final PixelFormat<ByteBuffer> pf = PixelFormat.getByteRgbInstance();
//
//
//        //        SwingUtilities.invokeLater(new Runnable() {
////            @Override
////            public void run() {
//        final JFrame win = new JFrame("Swing");
//        win.setSize(width, height);
//        win.setLocationRelativeTo(null);
//
//        GLProfile glProfile = GLProfile.getDefault();
//        GLCapabilities glCapabilities = new GLCapabilities(glProfile);
//        final GLCanvas glcanvas = new GLCanvas(glCapabilities);
////        final GLWindow newtWindow = new  GLWindow(glCapabilities);
//        glcanvas.addGLEventListener(new GLEventListener() {
//            //        newtWindow.addGLEventListener(new GLEventListener() {
//            @Override
//            public void init(GLAutoDrawable glad) {
//                GL2 gl = (GL2) glad.getContext().getGL();
//                gl.glClearColor(.1f, .1f, .1f, .1f);
//            }
//
//            @Override
//            public void dispose(GLAutoDrawable glad) {
//            }
//
//            @Override
//            public void display(GLAutoDrawable glad) {
//                long startTime = System.currentTimeMillis();
//                GL2 gl = (GL2) glad.getContext().getGL();
//                gl.glColor4f(1, 0, 0, 1);
//                gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
//                gl.glBegin(GL2.GL_TRIANGLES);
//                gl.glVertex2f(0, 0);
//                gl.glVertex2f(1, 0);
//                gl.glVertex2f(0, 1);
//                gl.glEnd();
//
//                gl.glReadBuffer(GL2.GL_FRONT);
//                width = glcanvas.getWidth();
//                height = glcanvas.getHeight();
//                if (byteBuffer == null) {
//                    byteBuffer = Buffers.newDirectByteBuffer(width * height * 3);
//                }
//
//
//                gl.glReadPixels(0, 0, width, height, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, byteBuffer);
////                Thread
////
////                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
////                int[] imageData = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
////                for (int y = 0; y < height; y++) {
////                    for (int x = 0; x < width; x++) {
////                        int b = 2 * byteBuffer.get();
////                        int g = 2 * byteBuffer.get();
////                        int r = 2 * byteBuffer.get();
////
////                        imageData[(height - y - 1) * width + x] = (r << 16) | (g << 8) | b | 0xFF000000;
////                    }
////                }
////                System.out.println("copied color buffer");
////                try {
////                    ImageIO.write(bufferedImage, "png", new File("C:\\Users\\arnaud\\Desktop\\todel.png"));
////                } catch (IOException ex) {
////                    System.out.println("Could not save image because " + ex);
////                }
////                byte[] bytes = new byte[width * height * 3];
////                PixelWriter pix = new PixelWriter() {}
////                byteBuffer.get(bytes);
////                bytes[0] = (byte)255;
//
//                Thread t = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
////                        try {
////                            Thread.sleep(1000);
////                        } catch (InterruptedException ex) {
////                            Logger.getLogger(Todel.class.getName()).log(Level.SEVERE, null, ex);
////                        }
//                        writableImage.getPixelWriter().setPixels(0, 0, width, height, pf, byteBuffer, width * 3);
//                    }
//                });
//                t.start();
////                writableImage.getPixelWriter().setPixels(0, 0, width, height, pf, byteBuffer, width * 3);
//                //                for (int y = 0; y < height; y++) {
//                //                    for (int x = 0; x < width; x++) {
//                //                        int b = 2 * byteBuffer.get();
//                //                        int g = 2 * byteBuffer.get();
//                //                        int r = 2 * byteBuffer.get();
//                //                        writableImage.getPixelWriter().setColor(x, y, new Color(r/255f, g/255f, b/255f, 1));
//                //                    }
//                //                }
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException ex) {
////                    Logger.getLogger(Todel.class.getName()).log(Level.SEVERE, null, ex);
////                }
//                System.out.println("Displayed in " + (System.currentTimeMillis() - startTime) + " ms");
//            }
//
//            @Override
//            public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
//            }
//        });
//        win.getContentPane().add(glcanvas);
//        win.setVisible(true);
//        win.setVisible(false);
//        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////            }
////        });
//
//        Button btn = new Button();
//        btn.setText("Reraw");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                glcanvas.display();
//            }
//        });
//
//
//
//        ImageView imgView = new ImageView(writableImage);
//
//        Pane root = new FlowPane();
//        root.getChildren().add(btn);
////        root.getChildren().add(new Button("gg"));
//        root.getChildren().add(imgView);
//
//        Scene scene = new Scene(root, 800, 600);
//
//        primaryStage.setTitle("JavaFX");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        primaryStage.toFront();
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent t) {
//                win.dispose();
//            }
//        });
//
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
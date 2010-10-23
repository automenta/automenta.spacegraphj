/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automenta.netention.demo.physics;

import javax.vecmath.Vector3f;
import automenta.spacegraph.physics.PhysicsController;
import automenta.spacegraph.physics.PhysicsPanel;
import automenta.spacegraph.swing.SwingWindow;
import com.bulletphysics.demos.opengl.GLDebugDrawer;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import automenta.spacegraph.physics.PhysicsApp;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import static com.bulletphysics.demos.opengl.IGL.*;

/**
 *
 * @author seh
 */
abstract public class DefaultPhysicsApp extends PhysicsApp {

    // keep the collision shapes, for deletion/cleanup
    //protected ObjectArrayList<CollisionShape> collisionShapes = new ObjectArrayList<CollisionShape>();
    protected BroadphaseInterface broadphase;
    protected CollisionDispatcher dispatcher;
    protected ConstraintSolver solver;
    protected DefaultCollisionConfiguration collisionConfiguration;
    public final static Vector3f zero3 = new Vector3f(0, 0, 0);

    public DefaultPhysicsApp() {
        super();
    }

    public DynamicsWorld getSpace() {
        return dynamicsWorld;
    }

    @Override
    public void clientMoveAndDisplay() {
        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // simple dynamics world doesn't handle fixed-time-stepping
        float ms = getDeltaTimeMicroseconds();

        double s = ms / 1000000f;
        
        // step the simulation
        if (dynamicsWorld != null) {
            dynamicsWorld.stepSimulation((float)s);
            // optional but useful: debug drawing
            dynamicsWorld.debugDrawWorld();
        }

        renderme(s);

        //glFlush();
        //glutSwapBuffers();
    }

//    @Override
//    public void displayCallback() {
//        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//
//        renderme();
//
//        // optional but useful: debug drawing to detect problems
//        if (dynamicsWorld != null) {
//            dynamicsWorld.debugDrawWorld();
//        }
//
//        //glFlush();
//        //glutSwapBuffers();
//    }


    public void initPhysics() {
        setCameraDistance(50f);

        // collision configuration contains default setup for memory, collision setup
        collisionConfiguration = new DefaultCollisionConfiguration();

        // use the default collision dispatcher. For parallel processing you can use a diffent dispatcher (see Extras/BulletMultiThreaded)
        dispatcher = new CollisionDispatcher(collisionConfiguration);

        broadphase = new DbvtBroadphase();

        // the default constraint solver. For parallel processing you can use a different solver (see Extras/BulletMultiThreaded)
        SequentialImpulseConstraintSolver sol = new SequentialImpulseConstraintSolver();
        solver = sol;

        // TODO: needed for SimpleDynamicsWorld
        //sol.setSolverMode(sol.getSolverMode() & ~SolverMode.SOLVER_CACHE_FRIENDLY.getMask());

        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);

        getSpace().setGravity(zero3);

        initApp();

        clientResetScene();
    }
    
    abstract protected void initApp();

    public static void start(final PhysicsApp app) {
        PhysicsPanel surface = new PhysicsPanel();
        app.init(surface);
        app.getDynamicsWorld().setDebugDrawer(new GLDebugDrawer(surface));
        new PhysicsController(surface, app);
        new SwingWindow(surface, 800, 600, true);
    }
    
}

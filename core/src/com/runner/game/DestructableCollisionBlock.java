package com.runner.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by William on 09/02/2016.
 */
public class DestructableCollisionBlock {

    private boolean commissioned;

    public boolean isCommissioned() {
        return commissioned;
    }

    private DestructableComponent bl;
    private DestructableComponent br;
    private DestructableComponent tl;
    private DestructableComponent tr;

    public DestructableCollisionBlock() {

        bl = new DestructableComponent();
        br = new DestructableComponent();
        tl = new DestructableComponent();
        tr = new DestructableComponent();

    }

    public void CommissionDestructable(Rectangle collisionBlock) {
        // create four collision components
        // and enable them
        bl.CommissionDestructableComponent(DestructableManager.Location.BottomLeft, collisionBlock);
        br.CommissionDestructableComponent(DestructableManager.Location.BottomRight, collisionBlock);
        tl.CommissionDestructableComponent(DestructableManager.Location.TopLeft, collisionBlock);
        tr.CommissionDestructableComponent(DestructableManager.Location.TopRight, collisionBlock);

        commissioned = true;

    }

    public void update(float delta) {
        if (commissioned) {
            bl.update(delta);
            br.update(delta);
            tl.update(delta);
            tr.update(delta);
        }
    }

    public void renderDebug(ShapeRenderer shapeRenderer) {
        if (commissioned) {
            bl.renderDebug(shapeRenderer);
            br.renderDebug(shapeRenderer);
            tl.renderDebug(shapeRenderer);
            tr.renderDebug(shapeRenderer);
        }
    }

    public void CheckIfOutOfCommission() {

        if (commissioned) {
            if (bl.OutOfScope() && br.OutOfScope() && tl.OutOfScope() && tr.OutOfScope() ) {
                commissioned = false; // recycle for reuse
            }
        }
    }
}

package net.darktree.stylishoccult.entities;

import net.darktree.stylishoccult.utils.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.LeashKnotEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

public class SparkEntityRenderer extends EntityRenderer<SparkEntity> {

    private static final Identifier TEXTURE = new ModIdentifier( "textures/particle/lava_spark.png" );
    private static final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);

    public SparkEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    public void render(SparkEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            matrixStack.push();
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            matrixStack.translate(0, 0.3, 0);
            matrixStack.multiply(this.dispatcher.getRotation());
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
            MatrixStack.Entry entry = matrixStack.peek();
            Matrix4f matrix4f = entry.getModel();
            Matrix3f matrix3f = entry.getNormal();
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);

            vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 0, 0, 1);
            vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 0, 1, 1);
            vertex(vertexConsumer, matrix4f, matrix3f, i, 1.0F, 1, 1, 0);
            vertex(vertexConsumer, matrix4f, matrix3f, i, 0.0F, 1, 0, 0);

            matrixStack.pop();
            super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        }

    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float f, int j, int k, int l) {
        vertexConsumer.vertex(matrix4f, f - 0.5F, (float)j - 0.5F, 0.0F).color(255, 255, 255, 255).texture((float)k, (float)l).overlay(OverlayTexture.DEFAULT_UV).light(i).normal(matrix3f, 0.0F, 1.0F, 0.0F).next();
    }

    public Identifier getTexture(SparkEntity leashKnotEntity) {
        return TEXTURE;
    }

}
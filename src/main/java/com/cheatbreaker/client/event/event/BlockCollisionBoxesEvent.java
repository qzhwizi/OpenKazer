package com.cheatbreaker.client.event.event;

import java.util.List;
import net.minecraft.util.AxisAlignedBB;

public class BlockCollisionBoxesEvent extends com.cheatbreaker.client.event.EventBus.aInner {
   private final List<AxisAlignedBB> boundingBoxes;
   private final int x;
   private final int y;
   private final int z;

   public BlockCollisionBoxesEvent(List<AxisAlignedBB> var1, int var2, int var3, int var4) {
      this.boundingBoxes = var1;
      this.x = var2;
      this.y = var3;
      this.z = var4;
   }

   public List<AxisAlignedBB> a() {
      return this.boundingBoxes;
   }

   public int b() {
      return this.x;
   }

   public int c() {
      return this.y;
   }

   public int d() {
      return this.z;
   }
}

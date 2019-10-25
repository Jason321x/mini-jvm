package com.gxk.jvm.instruction;

import com.gxk.jvm.rtda.Frame;

public class FConst1Inst implements Instruction {

  @Override
  public void execute(Frame frame) {
    frame.operandStack.pushFloat(1.0f);
  }
}

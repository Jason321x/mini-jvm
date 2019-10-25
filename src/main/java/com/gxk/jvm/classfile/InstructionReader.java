package com.gxk.jvm.classfile;

import com.gxk.jvm.classfile.cp.DoubleCp;
import com.gxk.jvm.classfile.cp.IntegerCp;
import com.gxk.jvm.classfile.cp.LongCp;
import com.gxk.jvm.classfile.cp.StringCp;
import com.gxk.jvm.instruction.*;
import com.gxk.jvm.util.Utils;

import java.io.DataInputStream;
import java.io.IOException;

public abstract class InstructionReader {

  public static Instruction read(int opCode, DataInputStream stream, ConstantPool constantPool) throws IOException {
    switch (opCode) {
      case 0x0:
        return new NopInst();
      case 0x1:
        return new AconstNullInst();
      case 0x2:
        return new IConstM1Inst();
      case 0x3:
        return new IConst0Inst();
      case 0x4:
        return new IConst1Inst();
      case 0x5:
        return new IConst2Inst();
      case 0x6:
        return new IConst3Inst();
      case 0x7:
        return new IConst4Inst();
      case 0x8:
        return new IConst5Inst();
      case 0x9:
        return new Lconst0Inst();
      case 0xa:
        return new Lconst1Inst();
      case 0xb:
        return new FConst2Inst();
      case 0xd:
        return new FConst2Inst();
      case 0xe:
        return new DConst0Inst();
      case 0xf:
        return new DConst1Inst();

      case 0x10:
        return new BiPushInst(stream.readByte());
      case 0x11:
        return new SiPushInst(stream.readShort());
      case 0x12:
        int index = stream.readUnsignedByte();
        ConstantInfo info = constantPool.infos[index - 1];
        switch (info.infoEnum) {
          case CONSTANT_String:
            int stringIndex = ((StringCp) info).stringIndex;
            String string = Utils.getString(constantPool, stringIndex);
            return new LdcInst(null, string);
          case CONSTANT_Integer:
            return new LdcInst(((IntegerCp) info).val, null);
          case CONSTANT_Class:
            return new LdcInst(null, info);
        }
        throw new IllegalStateException();
        // TODO
      case 0x14:
        int ldwIdx = stream.readUnsignedShort();
        ConstantInfo ldwInfo = constantPool.infos[ldwIdx - 1];
        switch (ldwInfo.infoEnum) {
          case CONSTANT_Double:
            return new Ldc2wInst(null, ((DoubleCp) ldwInfo).val);
          case CONSTANT_Long:
            return new Ldc2wInst(((LongCp) ldwInfo).val, null);
        }
        throw new IllegalStateException(ldwInfo.toString());
      case 0x15:
        return new IloadNInst(stream.readUnsignedByte());
      case 0x16:
        return new LloadInst(stream.readUnsignedByte());
      case 0x17:
        return new FLoadInst(stream.readUnsignedByte());
      case 0x18:
        return new DLoadInst(stream.readUnsignedByte());
      case 0x19:
        return new ALoadInst(stream.readUnsignedByte());
      case 0x1a:
        return new ILoad0Inst();
      case 0x1b:
        return new ILoad1Inst();
      case 0x1c:
        return new ILoad2Inst();
      case 0x1d:
        return new ILoad3Inst();
      case 0x1e:
        return new LLoad0Inst();
      case 0x1f:
        return new LLoad1Inst();

      case 0x20:
        return new LLoad2Inst();
      case 0x21:
        return new LLoad3Inst();
      case 0x22:
        return new FLoad0Inst();
      case 0x23:
        return new FLoad1Inst();
      case 0x24:
        return new FLoad2Inst();
      case 0x25:
        return new FLoad3Inst();
      case 0x26:
        return new DLoad0Inst();
      case 0x27:
        return new DLoad1Inst();
      case 0x28:
        return new DLoad2Inst();
      case 0x29:
        return new DLoad3Inst();
      case 0x2a:
        return new ALoad0Inst();
      case 0x2b:
        return new ALoad1Inst();
      case 0x2c:
        return new ALoad2Inst();
      case 0x2d:
        return new ALoad3Inst();

      case 0x32:
        return new AALoadInst();
      case 0x36:
        return new IStoreNInst(stream.readUnsignedByte());
      case 0x3b:
        return new IStore0Inst();
      case 0x3c:
        return new IStore1Inst();
      case 0x3d:
        return new IStore2Inst();
      case 0x3e:
        return new IStore3Inst();

      case 0x40:
        return new LStore1Inst();
      case 0x45:
        return new FStore2Inst();
      case 0x4b:
        return new AStore0Inst();
      case 0x4c:
        return new AStore1Inst();
      case 0x4d:
        return new AStore2Inst();
      case 0x4e:
        return new AStore3Inst();

      case 0x57:
        return new PopInst();
      case 0x59:
        return new DupInst();

      case 0x9a:
        return new IfNeInst(stream.readShort());
      case 0x9b:
        return new IfLtInst(stream.readShort());
      case 0x9c:
        return new IfGeInst(stream.readShort());

      case 0xa2:
        return new IfICmpGeInst(stream.readShort());
      case 0xa3:
        return new IfICmpGtInst(stream.readShort());
      case 0xa4:
        return new IfICmpLeInst(stream.readShort());

      case 0x9e:
        return new IfLeInst(stream.readShort());
      case 0x9f:
        return new IfICmpEqInst(stream.readShort());

      case 0xa0:
        return new IfICmpNeInst(stream.readShort());


      case 0x94:
        return new LCmpInst();

      case 0x60:
        return new IAddInst();
      case 0x61:
        return new LAddInst();
      case 0x64:
        return new ISubInst();

      case 0x84:
        return new IIncInst(stream.readUnsignedByte(), stream.readUnsignedByte());

      case 0x99:
        return new IfEqInst(stream.readShort());

      case 0xa6:
        return new IfACmpNeInst(stream.readShort());
      case 0xa7:
        return new Goto1Inst(stream.readShort());
      case 0xac:
        return new IReturnInst();

      case 0xb0:
        return new AReturnInst();
      case 0xb1:
        return new ReturnInst();
      case 0xb2:
        int gsIndex = stream.readUnsignedShort();
        return new GetStaticInst(
          Utils.getClassNameByFieldDefIdx(constantPool, gsIndex),
          Utils.getMethodNameByFieldDefIdx(constantPool, gsIndex),
          Utils.getMethodTypeByFieldDefIdx(constantPool, gsIndex)
        );
      case 0xb3:
        int psIndex = stream.readUnsignedShort();
        return new PutStaticInst(
          Utils.getClassNameByFieldDefIdx(constantPool, psIndex),
          Utils.getMethodNameByFieldDefIdx(constantPool, psIndex),
          Utils.getMethodTypeByFieldDefIdx(constantPool, psIndex)
        );
      case 0xb4:
        int gfIndex = stream.readUnsignedShort();
        return new GetFieldInst(
          Utils.getClassNameByFieldDefIdx(constantPool, gfIndex),
          Utils.getMethodNameByFieldDefIdx(constantPool, gfIndex),
          Utils.getMethodTypeByFieldDefIdx(constantPool, gfIndex)
        );
      case 0xb5:
        int pfIndex = stream.readUnsignedShort();
        return new PutFieldInst(
          Utils.getClassNameByFieldDefIdx(constantPool, pfIndex),
          Utils.getMethodNameByFieldDefIdx(constantPool, pfIndex),
          Utils.getMethodTypeByFieldDefIdx(constantPool, pfIndex)
        );
      case 0xb6:
        int ivIndex = stream.readUnsignedShort();
        return new InvokeVirtualInst(
          Utils.getClassNameByMethodDefIdx(constantPool, ivIndex),
          Utils.getMethodNameByMethodDefIdx(constantPool, ivIndex),
          Utils.getMethodTypeByMethodDefIdx(constantPool, ivIndex)
        );
      case 0xb7:
        int isIndex = stream.readUnsignedShort();
        return new InvokeSpecialInst(
          Utils.getClassNameByMethodDefIdx(constantPool, isIndex),
          Utils.getMethodNameByMethodDefIdx(constantPool, isIndex),
          Utils.getMethodTypeByMethodDefIdx(constantPool, isIndex)
        );

      case 0xb8:
        int mdIdx = stream.readUnsignedShort();
        return new InvokeStaticInst(
          Utils.getClassNameByMethodDefIdx(constantPool, mdIdx),
          Utils.getMethodNameByMethodDefIdx(constantPool, mdIdx),
          Utils.getMethodTypeByMethodDefIdx(constantPool, mdIdx)
        );
      case 0xb9:
        int iiIdx = stream.readUnsignedShort();
        return new InvokeInterfaceInst(
          Utils.getClassNameByIMethodDefIdx(constantPool, iiIdx),
          Utils.getMethodNameByIMethodDefIdx(constantPool, iiIdx),
          Utils.getMethodTypeByIMethodDefIdx(constantPool, iiIdx),
          stream.readUnsignedByte(),
          stream.readUnsignedByte()
        );

      case 0xbb:
        return new NewInst(Utils.getClassName(constantPool, stream.readUnsignedShort()));
      case 0xbe:
        return new ArrayLengthInst();
      case 0xbf:
        return new AThrowInst();

      case 0xc0:
        return new CheckcastInst(Utils.getClassName(constantPool, stream.readUnsignedShort()));
      case 0xc2:
        return new MonitorEnterInst();
      case 0xc3:
        return new MonitorExitInst();
      case 0xc6:
        return new IfNullInst(stream.readShort());
      case 0xc7:
        return new IfNonNullInst(stream.readShort());

      default:
        return null;
//        throw new UnsupportedOperationException("unknown op code");
    }
  }
}

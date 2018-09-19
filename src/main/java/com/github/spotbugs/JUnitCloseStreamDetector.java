package com.github.spotbugs;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.ba.ClassContext;
import edu.umd.cs.findbugs.ba.XClass;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;
import edu.umd.cs.findbugs.classfile.CheckedAnalysisException;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.Global;
import edu.umd.cs.findbugs.detect.*;
import org.apache.bcel.Const;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantString;
import org.apache.bcel.classfile.JavaClass;

import java.util.ArrayList;

public class JUnitCloseStreamDetector extends OpcodeStackDetector {
    private final BugReporter bugReporter;

    public JUnitCloseStreamDetector(BugReporter bugReporter) { this.bugReporter = bugReporter; }

    @Override
    public void visitClassContext(ClassContext classContext) {
        if (!enabled()) {
            return;
        }


        System.out.println("---------");
        System.out.println("JUnitCloseStreamDetector visitClassContext");
        System.out.println("---------");

        JavaClass jClass = classContext.getJavaClass();
        XClass xClass = classContext.getXClass();
        try {
            if (!isJunit3TestCase(xClass)) {
                return;
            }

//            ClassDescriptor desc = xClass.getClassDescriptor();
//            String fullClassNames[] = desc.getClassName().split("/");
//            String className = fullClassNames[fullClassNames.length-1];

            jClass.accept(this);
        } catch (ClassNotFoundException cnfe) {
            bugReporter.reportMissingClass(cnfe);
        }
    }

    @Override
    public void sawOpcode(int seen) {
        String methodName = getMethod().getName();

        System.out.println("method = " + methodName);
        if(methodName.equals("setUp")) {

        } else if (methodName.equals("tearDown")) {

        }

        printOpCode(seen);
    }

    private boolean enabled() {
        return true;
    }

    private boolean isJunit3TestCase(XClass jClass) throws ClassNotFoundException {
        ClassDescriptor sDesc = jClass.getSuperclassDescriptor();
        if (sDesc == null) {
            return false;
        }
        String sName = sDesc.getClassName();
        if (sName.equals("junit/framework/TestCase")) {
            return true;
        }
        if (sName.equals("java/lang/Object")) {
            return false;
        }

        try {
            XClass sClass = Global.getAnalysisCache().getClassAnalysis(XClass.class, sDesc);
            if (sClass == null) {
                return false;
            }
            return isJunit3TestCase(sClass);
        } catch (CheckedAnalysisException e) {
            return false;
        }
    }


}
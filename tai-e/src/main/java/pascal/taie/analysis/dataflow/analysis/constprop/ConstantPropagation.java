/*
 * Tai-e: A Static Analysis Framework for Java
 *
 * Copyright (C) 2022 Tian Tan <tiantan@nju.edu.cn>
 * Copyright (C) 2022 Yue Li <yueli@nju.edu.cn>
 *
 * This file is part of Tai-e.
 *
 * Tai-e is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * Tai-e is distributed in the hope that it will be useful,but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Tai-e. If not, see <https://www.gnu.org/licenses/>.
 */

package pascal.taie.analysis.dataflow.analysis.constprop;

import pascal.taie.analysis.dataflow.analysis.AbstractDataflowAnalysis;
import pascal.taie.analysis.graph.cfg.CFG;
import pascal.taie.config.AnalysisConfig;
import pascal.taie.ir.IR;
import pascal.taie.ir.exp.*;
import pascal.taie.ir.stmt.DefinitionStmt;
import pascal.taie.ir.stmt.Stmt;
import pascal.taie.language.type.PrimitiveType;
import pascal.taie.language.type.Type;
import pascal.taie.util.AnalysisException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class ConstantPropagation extends
        AbstractDataflowAnalysis<Stmt, CPFact> {

    public static final String ID = "constprop";

    public ConstantPropagation(AnalysisConfig config) {
        super(config);
    }

    @Override
    public boolean isForward() {
        return true;
    }

    @Override
    public CPFact newBoundaryFact(CFG<Stmt> cfg) {
        // TODO - finish me
        CPFact cpfact = new CPFact();
        for(Var v : cfg.getIR().getParams()){
            cpfact.update(v,Value.getNAC());
        }
        return cpfact;
    }

    @Override
    public CPFact newInitialFact() {
        // TODO - finish me
        return new CPFact();
    }

    @Override
    public void meetInto(CPFact fact, CPFact target) {
        // TODO - finish me
        //如果一个变量只有一个有的情况如何处理
        for(Var key : fact.keySet()){
            Value a = fact.get(key);
            Value b = fact.get(key);
            target.update(key,meetValue(a,b));
        }
    }

    /**
     * Meets two Values.
     */
    public Value meetValue(Value v1, Value v2) {
        // TODO - finish me
        if(v1.isNAC() || v2.isNAC()){
            return Value.getNAC();
        }

        if(v1.isUndef() || v2.isUndef()){
            return Value.getUndef();
        }

        int a = v2.getConstant();
        int b = v2.getConstant();
        if(a == b){
            return Value.makeConstant(a);
        }else{
            return Value.getNAC();
        }
    }

    @Override
    public boolean transferNode(Stmt stmt, CPFact in, CPFact out) {
        // TODO - finish me
        return false;
    }

    /**
     * @return true if the given variable can hold integer value, otherwise false.
     */
    public static boolean canHoldInt(Var var) {
        Type type = var.getType();
        if (type instanceof PrimitiveType) {
            switch ((PrimitiveType) type) {
                case BYTE:
                case SHORT:
                case INT:
                case CHAR:
                case BOOLEAN:
                    return true;
            }
        }
        return false;
    }

    /**
     * Evaluates the {@link Value} of given expression.
     *
     * @param exp the expression to be evaluated
     * @param in  IN fact of the statement
     * @return the resulting {@link Value}
     */
    public static Value evaluate(Exp exp, CPFact in) {
        // TODO - finish me
        Type type = exp.getType();
        BinaryExp Bexp = null;

        if(exp instanceof Var){
            return in.get((Var) exp);
        }else if(exp instanceof IntLiteral){
            int v = ((IntLiteral) exp).getValue();
            return Value.makeConstant(v);
        }else if(exp instanceof BinaryExp){
            BinaryExp.Op op = ((BinaryExp) exp).getOperator();
            Var left = ((BinaryExp) exp).getOperand1();
            Var right = ((BinaryExp) exp).getOperand2();
            Value v1 = in.get(left);
            Value v2 = in.get(right);
            if(v1.isNAC() || v2.isNAC()){
                return Value.getNAC();
            }else if(v1.isConstant() && v2.isConstant()){
                int a = v1.getConstant();
                int b = v2.getConstant();
                if(op.equals(ArithmeticExp.Op.ADD)){

                }else if(op.equals(ArithmeticExp.Op.SUB)){

                }else if(op.equals(ArithmeticExp.Op.MUL)){

                }else if(op.equals(ArithmeticExp.Op.DIV)){

                }else if(op.equals(BitwiseExp.Op.AND)){

                }else if(op.equals(BitwiseExp.Op.OR)){

                }else if(op.equals(BitwiseExp.Op.XOR)){

                }else if(op.equals(ConditionExp.Op.EQ)){

                }else if(op.equals(ConditionExp.Op.GE)){

                }else if(op.equals(ConditionExp.Op.GT)){

                }else if(op.equals(ConditionExp.Op.LE)){

                }else if(op.equals(ConditionExp.Op.LT)){

                }else if(op.equals(ConditionExp.Op.NE)){

                }else if(op.equals(ShiftExp.Op.SHL)){

                }else if(op.equals(ShiftExp.Op.SHR)){

                }else if(op.equals(ShiftExp.Op.USHR)){

                }
            }else{
                return Value.getUndef();
            }

        }
        return Value.getNAC();
    }
}

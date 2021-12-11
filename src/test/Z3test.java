package test;

import com.microsoft.z3.*;
//linux: add DYLD_LIBRARY_PATH=...../z3/bin to environment variables
//windows: add .../z3/bin to PATH

public class Z3test {

    public static void main(String[] args) throws InterruptedException {

        final Context context = new Context();
        final Solver solver = context.mkSimpleSolver();

        final IntExpr a = context.mkIntConst("a");
        final IntExpr b = context.mkIntConst("b");
        final BoolExpr expr = context.mkEq(a, context.mkAdd(b,context.mkInt(5)));
        final BoolExpr expr2 = context.mkGe(a, context.mkInt(8));
        final BoolExpr expr3 = context.mkEq(a, b);
        solver.assertAndTrack(expr, context.mkBoolConst("e1"));
        solver.assertAndTrack(expr2, context.mkBoolConst("e2"));
        solver.assertAndTrack(expr3, context.mkBoolConst("e3"));
        //solver.add(expr2);
        //solver.add(expr3);
        Status status = solver.check();
        if (status==Status.SATISFIABLE) {
            final Model model = solver.getModel();
            System.out.println(model.getConstInterp(a));
            System.out.println(model.getConstInterp(b));

        }
        if (status==Status.UNKNOWN) {
            System.out.println("Unknown solver status");
        }
        if (status==Status.UNSATISFIABLE) {
            System.out.println("Unsolvable");
            final BoolExpr[] unSatCore = solver.getUnsatCore();
            for (BoolExpr bx : unSatCore) {
                System.out.println(bx.toString());
            }

        }

        context.close();

    }

}
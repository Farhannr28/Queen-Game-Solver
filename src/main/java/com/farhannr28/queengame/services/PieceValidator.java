package com.farhannr28.queengame.services;

import com.farhannr28.queengame.models.Cell;

public class PieceValidator {
    private boolean[] mainDiagonal;
    private boolean[] antiDiagonal;
    private boolean[] filledRegions;
    private boolean[][] LArea;
    private boolean[] vertical;
    private boolean[] horizontal;
    private boolean[] extraParameters; //[L, Diagonal, Neighbor, +]
    private Cell[] knightMoves;
    private int neighbor;

    public PieceValidator(String piece, int row, int col, int numRegions){
        filledRegions = new boolean[numRegions];
        neighbor = 0;
        switch (piece){
            case "NEIGHBORQUEEN":
                extraParameters = new boolean[]{false, false, true, true};
                vertical = new boolean[col];
                horizontal = new boolean[row];
                break;
            case "QUEEN":
                extraParameters = new boolean[]{false, true, false, true};
                mainDiagonal = new boolean[col+row-1];
                antiDiagonal = new boolean[col+row-1];
                vertical = new boolean[col];
                horizontal = new boolean[row];
                break;
            case "ROOK":
                extraParameters = new boolean[]{false, false, false, true};
                vertical = new boolean[col];
                horizontal = new boolean[row];
                break;
            case "BISHOP":
                extraParameters = new boolean[]{false, true, false, false};
                mainDiagonal = new boolean[col+row-1];
                antiDiagonal = new boolean[col+row-1];
                break;
            case "KNIGHT":
                extraParameters = new boolean[]{true, false, false, false};
                LArea = new boolean[row][col];
                knightMoves = new Cell[]{new Cell(1, 2), new Cell(-1, 2), new Cell(1, -2), new Cell(-1, -2),
                                        new Cell(2, 1), new Cell(-2, 1), new Cell(2, -1), new Cell(-2, -1)};
                break;
        }
    }

    public boolean validateNonLinear(int r, int c){
        boolean ret = true;
        if (extraParameters[1]){
            ret = !mainDiagonal[r+c];
            ret = ret && !antiDiagonal[Math.abs(r-c)];
        } else if (extraParameters[0]){
            ret = !LArea[r][c];
        }
        return ret;
    }

    public boolean validateEmptyRegion(int r){
        return !filledRegions[r];
    }

    public boolean validateLinear(int x, boolean isRow){
        boolean ret = true;
        if (extraParameters[3]){
            if (isRow){
                ret = !vertical[x];
            } else {
                ret = !horizontal[x];
            }
        }
        if (extraParameters[2]){
            ret = ret && (x != neighbor-1) && (x != neighbor+1);
        }
        return ret;
    }

    public void setFilledRegions(int r, boolean b){
        this.filledRegions[r] = b;
    }

    public void setNeighbor(int x){
        this.neighbor = x;
    }

    public void setVertical(int x, boolean b){
        this.vertical[x] = b;
    }

    public void setHorizontal(int x, boolean b){
        this.horizontal[x] = b;
    }

    public void setDiagonal(int r, int c, boolean b){
        this.mainDiagonal[r+c] = b;
        this.antiDiagonal[Math.abs(r-c)] = b;
    }

    public void setLArea(int r, int c, boolean b){
        for (Cell cell : knightMoves){
            if (cell.getRow() + r < LArea.length && cell.getCol() + c < LArea[0].length && cell.getRow() + r >= 0 && cell.getCol() + c >= 0){
                this.LArea[cell.getRow() + r][cell.getCol() + c] = b;
            }
        }
    }

    public void debug(){
        for(boolean b : filledRegions){
            System.out.print(b + " ");
        }
        System.out.println(" ");
        for(boolean b : vertical){
            System.out.print(b + " ");
        }
        System.out.println(neighbor);
    }
}

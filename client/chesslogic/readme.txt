A rigorous formulation of the game of chess.
Leo Guan
Jan. 7, 2022
Version 1.0
This version omits castling, en passant, and other special rules.

Definition 1: A colour is an element of the set C={black, white}. We define the switch function s:C->C as follows:
s(black) = white, s(white) = black.
Definition 2: A type is an element of the set T={P, N, B, R, Q, K, null}.
Definition 3: A position is an element of Z/8Z x Z/8Z = {(0, 0), (0, 1), ... (7, 7)}. Call this set K. There exists a bijection between this set and the set of positions on a chessboard.
Definition 4: A piece is an ordered pair consisting of a colour and a type.
Definition 5: A tile is an ordered pair consisting of a position and a piece. 
Definition 6: A grid is a set of 64 distinct tiles such that the following is satisfied:
1. Each tile has a distinct position
2. Consider the set of all pieces on a position of this grid. Then there exists at most 1 king of each colour, 1 queen of each colour, etc.
We call the set of all grids G.
Definition 7: A state is an ordered pair consisting of a grid g and a colour c. We denote the set of all states as S, and write s = (g, c).
Definition 8: Given a grid g and a piece on a piece in this grid δ, define the function range_g(δ) to be the set of all tiles that δ can 'move to'. The specific value of range_g(δ) corresponds to the positions that a piece 'is attacking'. This function can be defined constructively for any piece on any grid g.
Definition 9: Given a grid g and a colour c, we say that the king of colour c is in check if the union of the ranges of all pieces of colour s(c) contains the tile that the king of colour c is on.
Theorem 1: Given a grid g and a tile containing a king k of colour c, define checkRange_g(k) as follows:
For each type t, consider the grid g' consisting of identical tiles as g except for the tile containing the king. For the tile containing the king in g, replace the king with a piece of type t!=null. Define checkRange_g(k) as the union of the ranges of this piece on the board g'. (i.e. consider a piece that can move like a queen and a knight simultaneously on g')
then the king of colour c is in check only if there exists a black piece in checkRange_g(k). Note that the converse is not necessarily true.
Proof: Omitted.
Definition 10: Given a state s = (g, c), a move for the colour c is a pair of tiles (t1, t2) such that the following is satisfied:
1. t1 != t2.
2. t1 contains a piece of colour c.
3. t2 is contained in the range of the piece on t1.
The set of all moves for a given state s is called M_s.
Definition 11: Given a state s = (g, c) and a move m = (t1, t2), the successor of the s using m is defined to be the following:
1. For each tile t != t1,t2, t is in a new grid called g'.
2. t1 contains a null piece in g'.
3. t2 contains the piece originally on t1 in g'.
4. c' = s(c).
The successor of s using m is then defined to be (g', c'). We call this state s'. We say that s1 is adjacent to s2 if there exists a move m such that s2 is the successor of s1 using m.
Definition 12: Given a state s = (g, c) and a move m = (t1, t2), we say that m is legal if the king of colour c on s' is not in check.
Remark: The set of legal moves is a subset of the set of moves.
Definition 13/14: Given a state s = (g, c), we say that the king of colour c is in checkmate if the each of the following is satisfied:
1. The king of colour c is in check.
2. The set of legal moves on s is empty.
We say that a stalemate exists if at least one of the following is satisfied: **Needs editing/improvement**
1. The set of legal moves on s is empty.
2. Every game with the current state as an element is nonterminating (see definition 15)
Definition 15: A game is a sequence of states s_1, s_2, ..., s_n with the following satisfied:
1. s_1 is the typical starting position.
2. If the sequence ends, then the final position is either a checkmate or a stalemate.
3. For each i with 1 <= i <= s_n-1, s_i is adjacent to s_i+1 with each move between them being legal.
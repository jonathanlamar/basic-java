# Extremely Simple Java Projects

I am an experienced Scala developer, and I need to learn Java for my new job.
Since my brain is scrambled eggs, I can only learn through implementing simple
projects.  These are some that I have chosen to (hopefully) knock out easily.

## Sudoku Solver

Goals:

1. Get familiar with java syntax.
2. Learn basic structure of java projects and maven build definitions.

Features:

1. Scrapes sudoku puzzles from [sudokuweb.org](sudokuweb.org).
2. Solves using a backtracking algorithm
3. Shows an ascii animation of the algorithm, slowed down to one move per 100ms.

## ASCII art

Goals:

?

Features:

1. Uses a ranking of ascii characters by brightness, based on the Hack font that
   I use on my computer.  This ranking was generated by converting the ttf file
   to bitmap using [this resource](https://onlineconvertfree.com/convert-format/ttf-to-bmp/).
2. User can set the size of the desired image in character width and height.
3. Should at least support jpeg, but hopefully png and gif.  For gif files,
   should convert each frame for converting back to ascii gif.

## OOP Tetris

Goals:

1. Learn libraries for graphics and GUI applications.

Features:

1. Graphical
2. Efficient encoding of gamestate for eventual use in ML/reinforcement
   learning.

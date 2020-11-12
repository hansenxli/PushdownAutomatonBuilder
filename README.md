# PushdownAutomatonBuilder
A java program that builds and tests pushdown automata


The first input file should contain the pda description. The first line should have the number of states, the number of transitions, the start state, and then the accept states. 
Each value should be separated by a space, but the accept states should be split with commas. Example:
numberOfStates numberOfTransitions startState acceptState1,acceptState2,acceptState3

Every line after should contain transition information. The following values should be separated by spaces.
startState nextState transitionChar charToPop charToPush

The second input file should begin with the number of strings to test. Every line after should contain a string to test equal to the number listed at the top of the file.

The program, when run, will return whether the strings are accepted by the PDA and will return true if they are accepted and false if they are rejected.

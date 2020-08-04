LTMS system find conflict in electrical circuits or CNF logic clauses.

1. Play
2. Select option:
    1- run example from lecture.
    2- run our small electrical circuits example
    3- find conflicts given paths to system' description and observation.
        enter limit of run time in minutes unit.  enter '0' for no limit.
        enter max conflict limit.
    4- run testing flow
        
       

--TESTING--
We added some testing scenarios of small boolean systems, you can find it on:
LTMS\src\main\resources\examples\Testing

You can add further scenarios but it need to keep the file names conventions:
- The prefix of the observation must be equals to the data description name for example:
    * System description: 2AndGatesSimpleSystem    --> System observation should be:
        * 2AndGatesSimpleObs0ConflictsMax0 or 2AndGatesSimpleObs1ConflictsMax1 etc
- You should mention the number of conflicts of the observation just before "Conflicts" on the file name
- You should mention the number of max conflicts you want the LTMS run with, this option allows you to check the consistency of the system for example:
    * Num of conflicts of the observation is: 2, and the max conflicts is: 1 , you should expect to get only 1 conflict
    * If the num of conflicts of the observation is: 3, and the max conflicts is: 5, you should expected to get 3 conflicts.


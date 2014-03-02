CS 4720 Web and Mobile Project
Web Service 1 ReadMe
Ke Cheng
Patrick Lynch
Ashley Schoen

Base Service Address: http://plato.cs.virginia.edu/~pel5xq/

Instructions on Usage: Format of all returned objects is JSON. When testing, make sure to insert (Pattern 1) before making subsequent queries due to the realtime nature of the data.

1) Pattern: http://plato.cs.virginia.edu/~pel5xq/insert/library/@lib/section/@sec/crowd/@crowd/noise/@noise
	Example: http://plato.cs.virginia.edu/~pel5xq/insert/library/Alderman/section/1/crowd/10/noise/9

	What you should pass: the library name, the library section, crowd level from 1-10, and noise level from 1-10
	What you should get back: a HTTP response code of 200

2) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/section/@sec/timespan/@minutes
   Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/section/4/timespan/40

    What to pass: library name, section name, time in minutes
    What to get back: average noise and crowd for a library  in a given section within the past given number of minutes 

3) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/timespan/@minutes
    Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/timespan/40

    What to pass: library name, time in minutes
    What to get back: average noise and crowd for an entire library within the past given number of minutes

4) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/section/@sec/day
	Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/section/4/day

	What to pass: library name, section name, day is just today
	What to get back: pattern of average noise and crowd for average day of the week as today (example, what does Rice commons look like on this (*current day of week*)) in a given section 

5) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/section/@sec/day/@day
    Example: http://plato.cs.virginia.edu/~pel5xq//library/Alderman/section/4/day/4

    what to pass: library name, section name, day of the week
	What to get back: pattern of average noise and crowd for average day of the week as given day(example, what does Rice commons look like on a Monday (for 1), Tuesday (for 2), etc.) for a given section 

6) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/day
   Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/day

   What to pass: library name, day is just today
   What to get back: pattern of average noise and crowd for average day of the week as today for entire library

7) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/day/@day
    Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/day/4

    What to pass: library name, day of the week
    What to get back: pattern of average noise and crowd for average given day of the week for entire library

8) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/section/@sec/timespan/@minutes/day
    Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/section/4/timespan/30/day

    What to pass: library name, library section, time in minutes
    What to get back: pattern of average noise and crowd for average day of the week as today for a library section within the past given number of minutes

9) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/section/@sec/timespan/@minutes/day/@day
    Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/section/4/timespan/30/day/4

    What to pass: library name, section name, time in minutes, day of the week
    What to get back: pattern of average noise and crowd for a library section within the past given number of minutes and on a given day of the week

10) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/timespan/@minutes/day
    Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/timespan/40/day

    What to pass: library name, time in minutes
    What to get back: pattern of average noise and crowd for average day of the week as today for a library within the past given number of minutes 

11) Pattern: http://plato.cs.virginia.edu/~pel5xq/library/@lib/timespan/@minutes/day/@day
    Example: http://plato.cs.virginia.edu/~pel5xq/library/Alderman/timespan/30/day/4
        
    What to pass: library name, time in minutes, day of week
    What to get back: pattern of average noise and crowd for average given day of the week as today for a library within the past given number of minutes 
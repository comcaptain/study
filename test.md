```q
endTime: .z.dt;
startTime: .z.dt - 1m;
select from (select countCombination: count i by a, b, c from getRows[`startTime`endTime`tableName`whereClause ! (startTime; endTime; `abc; "")]) where countCombination > 1
// Define the time range for the past month
endTime: .z.dt;
startTime: .z.dt - 1m;

// Construct the query using getRows
result: getRows[`startTime`endTime`tableName`whereClause ! (startTime; endTime; `abc; "1b")];

// Aggregation to count combinations of a, b, and c
aggregatedData: select countCombination: count i by a, b, c from result;

// Check for duplicates
duplicates: select from aggregatedData where countCombination > 1;

// Result
duplicates

```

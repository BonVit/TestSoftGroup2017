/*1st query*/
SELECT Employees.Name
FROM Employees, Employees AS Bosses
WHERE (
	(Employees.BossID = Bosses.EmployeeID)
	AND
	(Employees.Salary > Bosses.Salary)
);

/*2nd query*/
SELECT Employees.*
FROM Employees
INNER JOIN (
		(SELECT Employees.DepartmentID AS did, max(Employees.Salary) AS maxs
		FROM Employees
		GROUP BY Employees.DepartmentID) AS t)
ON (Employees.DepartmentID=t.did AND Employees.Salary=t.maxs);

/*3rd query*/
SELECT Departments.Name
FROM (Employees INNER JOIN Departments ON Employees.DepartmentID=Departments.DepartmentID)
	INNER JOIN (
		(SELECT Employees.DepartmentID as did, count(*) as num_emps
		FROM Employees
		GROUP BY Employees.DepartmentID) AS t
		)
	ON (Employees.DepartmentID=t.did)
WHERE t.num_emps<3
GROUP BY Departments.DepartmentID, Departments.Name;

/*4th query*/
SELECT Employees.*
FROM Employees LEFT JOIN 
((SELECT Employees.EmployeeID AS eID
FROM Employees INNER JOIN (
		(SELECT Employees.DepartmentID AS did, Employees.EmployeeID AS empID
		FROM Employees) AS Bosses)
ON (Employees.BossID=Bosses.empID AND Employees.DepartmentID=Bosses.did)) AS emps)
ON (Employees.EmployeeID = emps.eID)
WHERE emps.eID IS NULL;

/*5th query*/
SELECT Employees.DepartmentID, SUM(Employees.Salary) 
FROM Employees
GROUP BY Employees.DepartmentID;










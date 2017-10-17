package core;

/**
 * Class containing constants of SQL statements.
 * NOTE: Metadata? Hardcode?
 *
 * @version 1.0
 * @author vikirnt
 * @since October 2017
 */
abstract class Query {

    static final String
    CREATE =
            (
            "CREATE TABLE IF NOT EXISTS Candidates (" +
                    "first_name varchar(30) NOT NULL, " +
                    "last_name varchar(30) NOT NULL, " +
                    "post varchar(100) NOT NULL, " +
                    "stddiv varchar(10), " +
                    "votecount int NOT NULL" +
                    ");"
            ),

    SELECT =
            (
            "SELECT *, rowid FROM Candidates;"
            ),

    ADD =
            (
            "INSERT INTO Candidates VALUES " +
                    "(?, ?, ?, ?, ?);"
            ),

    SUB =
            (
            "DELETE FROM Candidates WHERE rowid = ? ;"
            ),

    EDIT =  (
            "UPDATE Candidates " +
                    "SET first_name = ?, " +
                    "last_name = ?, " +
                    "post = ?, " +
                    "stddiv = ?, " +
                    "votecount = ? " +
                    "WHERE rowid = ?" +
                    ";"
            ),

    CLEANSLATE =
            (
            "DELETE FROM Candidates;"
            ),

    VOTE =
            (
            "UPDATE Candidates " +
                    "SET votecount = votecount + 1 " +
                    "WHERE rowid = ?" +
                    ";"
            ),

    COUNT =
            (
            "SELECT COUNT(*) FROM Candidates;"
            ),

    GET =
            (
            "SELECT * FROM Candidates WHERE rowid = ?;"
            );

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Homework {

    private static final String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static final String MINIONS_TABLE_NAME = "minions_db";
    private Connection connection;
    private BufferedReader reader;

    public Homework() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }


    public void setConnection(String user, String password) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);

        connection = DriverManager
                .getConnection(CONNECTION_STRING + MINIONS_TABLE_NAME, properties);
    }

    public void getVillainsNamesEx2() throws SQLException {
        String query = "SELECT v.name, COUNT(mv.minion_id) AS 'count'\n" +
                "FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY v.id\n" +
                "HAVING count > 15\n" +
                "ORDER BY `count` DESC;";

        PreparedStatement statement = connection
                .prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            System.out.printf
                    ("%s %d%n", resultSet.getString("name"),
                            resultSet.getInt(2));
        }

    }

    public void getMinionNamesEx3() throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Enter villain id");
        int villainId = Integer.parseInt(reader.readLine());

        String villainName = getEntityNameById(villainId, "villains");
        if (villainName == null) {
            System.out.println("No villain with id " + villainId);
            return;
        }
        System.out.println("Villain: " + villainName);

        String query = "SELECT m.name, m.age FROM minions AS m\n" +
                "JOIN minions_villains mv on m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?";

        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setInt(1, villainId);

        ResultSet resultSet = statement.executeQuery();
        int counter = 1;
        while (resultSet.next()) {
            System.out.printf("%d .%s %d%n",
                    counter++, resultSet.getString("name"),
                    resultSet.getInt("age")
            );

        }
    }

    private String getEntityNameById(int entityID, String tableName) throws SQLException {
        String query = String.format("SELECT name FROM %s WHERE id = ?", tableName);
        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, entityID);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }

    public void addMinionEx4() throws IOException, SQLException {
        System.out.println("Enter minions info: Name, age, town name: ");
        System.out.print("Enter minion: ");
        String[] minionsInfo = reader.readLine().split("\\s+");

        System.out.print("Enter Villain: ");
        String villainName = reader.readLine();

        String minionName = minionsInfo[0];
        int age = Integer.parseInt(minionsInfo[1]);
        String townName = minionsInfo[2];

        int minionId = getEntityMinionNameById(minionName, "minions");
        int townId = getENtityIdByName(townName, "towns");
        int villainNameId = getENtityIdByGuruName(villainName, "villains");
        if (townId < 0) {
            insertEntityIntTowns(townName);
            System.out.printf("Town %s was added to the database.%n", townName);
        } else if (villainNameId < 0) {
            insertEntityIntVillains(villainName);
            System.out.printf("Villain %s was added to the database.%n",villainName);
        } else if (minionId<0){
            insertEntityIntMinion(minionName);
            System.out.printf("Successfully added %s to be minion of %s%n", minionName,villainName);
        }



    }

    private void insertEntityIntMinion(String minionName) throws SQLException {
        String query = "INSERT INTO minions(name) value(?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,minionName);
        statement.execute();

    }

    private void insertEntityIntVillains(String villainName) throws SQLException {
        String query = "INSERT INTO villains(name) value(?)";
        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1, villainName);
        statement.execute();
    }

    private void insertEntityIntTowns(String townName) throws SQLException {
        String query = "INSERT INTO towns(name) value(?)";
        PreparedStatement statement = connection
                .prepareStatement(query);

        statement.setString(1, townName);
        statement.execute();
    }

    private int getEntityMinionNameById(String minionName, String minionsTable) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?",minionsTable);
        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1, minionName);
        ResultSet resultSet = statement.executeQuery();
        return  resultSet.next() ? resultSet.getInt(1): - 1;


    }

    private int getENtityIdByGuruName(String guru, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);

        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1, guru);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getInt(1) : -1;

    }

    private int getENtityIdByName(String entityName, String tableName) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);

        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1, entityName);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    public void changeTownNameCasingEx5() throws IOException, SQLException {
        System.out.println("Entry country name: ");
        String countryName = reader.readLine();
        String query = "UPDATE towns SET name = UPPER(name) WHERE country = ?";

        PreparedStatement statement = connection
                .prepareStatement(query);
        statement.setString(1, countryName);
        int townsAffected = statement.executeUpdate();
        System.out.printf("%d town names were affected.%n",
                townsAffected);
    }

    public void increaseAgeWithStoreProcedureEx9() throws IOException, SQLException {
        System.out.println("Enter minion id: ");
        int minion = Integer.parseInt(reader.readLine());
        //TODO PRINT NAME AND AGE
        String query = "CALL usp_get_older(?)";

        CallableStatement callableStatement = connection
                .prepareCall(query);
        callableStatement.setInt(1, minion);

        callableStatement.execute();


    }
}

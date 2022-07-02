package pt.dioguin.feltzeconomy.utils;

import pt.dioguin.feltzeconomy.Main;
import pt.dioguin.feltzeconomy.objects.EcoPlayer;

import java.io.File;
import java.sql.*;

public class SQLite {

    private static Connection connection = null;
    private static Statement statement = null;

    public static void openConnection(){

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + Main.getInstance().getDataFolder() + File.separator + "database.db");
            System.out.println("A conexão com o SQLite foi estabelecida com sucesso.");

        } catch (ClassNotFoundException | SQLException e) {

            e.printStackTrace();
            System.out.println("Ocorreu um erro ao tentar conectar o SQLite.");

        }

    }

    public static void createTable(){

        try {

            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS FELTZECONOMY (PLAYER TEXT, AMOUNT DOUBLE);";
            statement.execute(sql);
            statement.close();
            System.out.println("Tabela criada com sucesso.");

        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Ocorreu um erro ao criar a tabela.");

        }

    }

    public static boolean hasPlayer(String player){

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM FELTZECONOMY WHERE PLAYER = ?");
            preparedStatement.setString(1, player);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }

    public static void savePlayers(){

        PreparedStatement preparedStatement = null;

        for (String player : EcoPlayer.getPlayers().keySet()){

            EcoPlayer ecoPlayer = EcoPlayer.getPlayers().get(player);

            try {

                if (hasPlayer(player)){
                    preparedStatement = connection.prepareStatement("UPDATE FELTZECONOMY SET AMOUNT = ? WHERE PLAYER = ?");
                    preparedStatement.setString(2, player);
                    preparedStatement.setDouble(1, ecoPlayer.getMoney());
                    preparedStatement.executeUpdate();
                }else{
                    preparedStatement = connection.prepareStatement("INSERT INTO FELTZECONOMY (PLAYER,AMOUNT) VALUES (?,?)");
                    preparedStatement.setString(1, player);
                    preparedStatement.setDouble(2, ecoPlayer.getMoney());
                    preparedStatement.executeUpdate();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public static void loadPlayers(){

        try {

            statement = connection.createStatement();
            String sql = "SELECT * FROM FELTZECONOMY;";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()){
                new EcoPlayer(rs.getString("PLAYER"), rs.getDouble("AMOUNT"));
            }

            rs.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

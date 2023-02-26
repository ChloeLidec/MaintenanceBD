import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurDeconnexion implements EventHandler<ActionEvent>{
    private AppliAllo45 app;
    private ConnexionMySQL conn;



    public ControleurDeconnexion(AppliAllo45 app,ConnexionMySQL co){
        this.app=app;
        this.conn=co;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            this.conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.app.quitte();

    }
}
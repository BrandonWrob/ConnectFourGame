import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UserStats
 * @author Dolly Jani 
 * @author Vishal Kommidi
 * @author Brandon Wroblewski
 */
public class UserStatsTest {

    /**
     * user stats for testing
     */
    private UserStats userStats;


    /**
     * BeforeEach method
     */
    @BeforeEach
    public void setUp() {
        int rows = 6;
        int columns = 7;
        userStats = new UserStats(rows, columns);
    }

    /**
     * Tests increment player pieces 
     */
    @Test
    public void testIncrementPlayerPieces() {
            
        userStats.incrementPlayerPieces("X");
        assertEquals(1, userStats.getPlayer1Pieces());
        userStats.incrementPlayerPieces("O");
        assertEquals(1, userStats.getPlayer2Pieces());
        userStats.incrementPlayerPieces("X");
        userStats.incrementPlayerPieces("X");
        assertEquals(3, userStats.getPlayer1Pieces());
        userStats.incrementPlayerPieces("A");
        assertEquals(3, userStats.getPlayer1Pieces());
        assertEquals(1, userStats.getPlayer2Pieces());
    }


}
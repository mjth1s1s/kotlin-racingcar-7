package racingcar
import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms

class Game {
    // 게임 시작
    fun start() {
        val carNames = inputCarNames()
        val roundCount = inputRoundCount()
        val race= Race(carNames, roundCount)
        race.runStart()
    }
    // 자동차 이름 입력
    private fun inputCarNames(): List<Car> {
        println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)")
        val carNames = Console.readLine().split(",").map {Car(it.trim())}
        duplicateNameError(carNames)
        return carNames
    }
    // 이름 중복시 예외 발생
    private fun duplicateNameError(carNames: List<Car>) {
        if (carNames.size != carNames.toSet().size) {
            throw IllegalArgumentException("자동차 이름은 중복될 수 없습니다.")
        }
    }
    // 시도할 횟수 입력
    private fun inputRoundCount(): Int {
        println("시도할 횟수는 몇 회인가요?")
        return Console.readLine().toIntOrNull() ?: throw IllegalArgumentException("잘못된 입력입니다.")
    }
}
// 자동차 클래스
data class Car(val name: String, var move: String = "") {
    // 자동차 이름 5자 넘을 경우 예외 발생
    init {
        nameError()
    }
    // 이름 예외 발생 메서드
    private fun nameError() {
        if (name.length > 5) {
            throw IllegalArgumentException("자동차 이름은 5자를 초과할 수 없습니다.")
        }
        if (name.isBlank()) {
            throw IllegalArgumentException("자동차 이름은 공백일 수 없습니다.")
        }
    }
    // 자동차가 움직이는 기능
    fun move() {
        if (Randoms.pickNumberInRange(0, 9) >= 4) {
            this.move += "-"
        }
    }
    // 자동차가 이동한 거리를 반환
    fun getMoveDistance(): Int {
        return this.move.length
    }
}

// 경주 관리 클래스
class Race(private val cars: List<Car>, private val roundCount: Int) {
    // 경주 시작
    fun runStart() {
        repeat(roundCount) {
            raceOnce()
        }
        announceWinner()
    }
    // 한 라운드의 경주 진행
    private fun raceOnce() {
        for (car in cars) {
            car.move()
            println("${car.name} : ${car.move}")
        }
        println()
    }
    // 최종 우승자 발표
    private fun announceWinner() {
        val longest = cars.maxByOrNull {it.getMoveDistance()}?.getMoveDistance() ?: 0
        val winner = cars.filter {it.getMoveDistance()==longest}.map {it.name}
        println("최종 우승자 : ${winner.joinToString(", ")}")
    }
}

fun main() {
    Game().start()
}
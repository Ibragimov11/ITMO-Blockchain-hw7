import com.google.common.hash.Hashing
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.nio.file.Files
import java.nio.file.Path

/**
 *@author Said Ibragimov on 28.12.2022 06:00
 */

fun main(args: Array<String>) {
    val parser = ArgParser("program")
    val file by parser.option(
        type = ArgType.String,
        fullName = "file"
    ).required()
    val ticketCount by parser.option(
        type = ArgType.Int,
        fullName = "numbilets"
    ).required()
    val param by parser.option(
        ArgType.Int,
        fullName = "parameter"
    ).required()

    parser.parse(args)

    val hashFunction = Hashing.sha256()

    Files.lines(Path.of(file)).map {
        val hash = hashFunction.newHasher()
            .putInt(param)
            .putString(it, Charsets.UTF_8)
            .hash()
        val ticketNumber = 1U + (hash.asInt().toUInt() % ticketCount.toUInt())

        "$it : $ticketNumber"
    }.forEach { println(it) }
}

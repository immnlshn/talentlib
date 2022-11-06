package dev.isohn.routes

import dev.isohn.models.Talents
import dev.isohn.models.talentsStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.talentsRoutes() {
    route("/talents") {
        get {
            if(talentsStorage.isNotEmpty()) {
                call.respond(talentsStorage)
            } else {
                call.respondText("No talents found", status = io.ktor.http.HttpStatusCode.NotFound)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val talent = talentsStorage.find { it.id == id } ?: return@get call.respondText(
                "No talent with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(talent)
        }
        post {
            val talent = call.receive<Talents>()
            talentsStorage.add(talent)
            call.respondText("Talent stored correctly", status = HttpStatusCode.Accepted)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (talentsStorage.removeIf { it.id == id }) {
                call.respondText("Talent removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}
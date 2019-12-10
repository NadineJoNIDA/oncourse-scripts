import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient

BASE_URL = ""
apiKey = ""
userId = ""


def enrolment = args.entity

// make or get students
def member = postMember(enrolment)

//get course id for group add
def courses = getCourses()

def course = courses.find { c -> c.title == enrolment.courseClass.course.name }

// get groups (classes) in course
def courseClasses = getGroups(course["id"])
def courseClass = courseClasses.find { cc -> cc.name == enrolment.courseClass.uniqueCode }

if (!courseClass) {
    //create groupClass if one does not exist
    courseClass = postGroup(enrolment, course["id"])
}

////add student to group
enrolMemberToGroup(member["id"], courseClass["id"])

def getCourses() {
    def client = new RESTClient(BASE_URL)
    client.headers["Authorization"] = "ECOACH-V1-SHA256 UserId=${userId}, UserToken=${apiKey}"
    client.headers["Content-Type"] = "application/json"
    client.headers["Accept"] = "application/json"

    client.request(Method.GET, ContentType.JSON) {
        uri.path = "/api/v1/courses"
        response.success = { resp, result ->
            return result
        }
    }
}

def getGroups(courseId) {

    def client = new RESTClient(BASE_URL)
    client.headers["Authorization"] = "ECOACH-V1-SHA256 UserId=${userId}, UserToken=${apiKey}"
    client.headers["Content-Type"] = "application/json"
    client.headers["Accept"] = "application/json"


    client.request(Method.GET, ContentType.JSON) {
        uri.path = "/api/v1/courses/${courseId}/groups"

        response.success = { resp, result ->
            return result
        }
    }
}

def postMember(enrolment) {
    def client = new RESTClient(BASE_URL)
    client.headers["Authorization"] = "ECOACH-V1-SHA256 UserId=${userId}, UserToken=${apiKey}"
    client.headers["Content-Type"] = "application/json"
    client.headers["Accept"] = "application/json"

    client.request(Method.POST, ContentType.JSON) {
        uri.path = "/api/v1/members"
        uri.query = [
                username : enrolment.student.contact.email,
                firstname: enrolment.student.contact.firstName,
                lastname : enrolment.student.contact.lastName,
                email    : enrolment.student.contact.email
        ]

        response.success = { resp, result ->
            return result
        }
    }
}

def postGroup(enrolment, courseId) {

    def client = new RESTClient(BASE_URL)
    client.headers["Authorization"] = "ECOACH-V1-SHA256 UserId=${userId}, UserToken=${apiKey}"
    client.headers["Content-Type"] = "application/json"
    client.headers["Accept"] = "application/json"

    client.request(Method.POST, ContentType.JSON) {
        uri.path = "/api/v1/courses/${courseId}/groups"
        body = [
                name: enrolment.courseClass.uniqueCode
        ]

        response.success = { resp, result ->
            return result
        }
    }
}

def enrolMemberToGroup(memberId, groupId) {

    def client = new RESTClient(BASE_URL)
    client.headers["Authorization"] = "ECOACH-V1-SHA256 UserId=${userId}, UserToken=${apiKey}"
    client.headers["Content-Type"] = "application/json"
    client.headers["Accept"] = "application/json"

    def id = [memberId] as int[]

    client.request(Method.POST, ContentType.JSON) {
        uri.path = "/api/v1/groups/${groupId}/students"
        body = [
                ids: id
        ]

        response.success = { resp, result ->
            return result
        }
    }
}
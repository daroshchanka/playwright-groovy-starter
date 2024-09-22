package github.daroshchanka.playwrightstarter.project.httpbin.utils.data.generators

import github.daroshchanka.playwrightstarter.project.httpbin.utils.data.AnythingDto
import net.datafaker.Faker

class AnythingGenerator {

  static AnythingDto generate() {
    Faker faker = new Faker()
    new AnythingDto().tap {
      keyString = faker.science().element()
      keyBoolean = faker.bool().bool()
      keyNumber = faker.number().numberBetween(0, 10000)
      keyArrayString = [
          faker.animal().name(),
          faker.animal().name(),
          faker.animal().name(),
      ]
      keyArrayObj = [
          [ name: faker.name().firstName(), c: 0, d: false ],
          [ name: faker.name().firstName(), c: 1, d: true ],
          [ name: faker.name().firstName(), c: 2, d: true ],
      ]
    }
  }
}

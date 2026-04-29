package nz.benlawrence.splatoontracker.data.models.coral

import kotlinx.serialization.Serializable

// MARK: - Request
@Serializable
data class SessionRequest(
  val state: String,
  val verifier: String,
  val authoriseUrl: String,
  val redirectURI: String
)

// MARK: - Response
@Serializable
data class SessionResponse(
  val coral: Coral,
  val splatnet: SplatNetAuthDataWrapper
)

data class SplatNetAuthDataWrapper(
  val data: SplatnetAuthData
)

data class Coral(
  val accountToken: AccountToken,
  val user: User
)

data class AccountToken(
  val access_token: String,
  val expires_in: Int,
  val id_token: String,
  val scope: List<String>,
  val token_type: String
)

data class User(
  val analyticsOptedIn: Boolean,
  val analyticsOptedInUpdatedAt: Int,
  val analyticsPermissions: AnalyticsPermissions,
  val birthday: String,
  val clientFriendsOptedIn: Boolean,
  val clientFriendsOptedInUpdatedAt: Int,
  val country: String,
  val createdAt: Int,
  val eachEmailOptedIn: EachEmailOptedIn,
  val emailOptedIn: Boolean,
  val emailOptedInUpdatedAt: Int,
  val emailVerified: Boolean,
  val gender: String,
  val iconUri: String,
  val id: String,
  val isChild: Boolean,
  val language: String,
  val nickname: String,
  val phoneNumberEnabled: Boolean,
  val region: Any,
  val screenName: String,
  val timezone: Timezone,
  val updatedAt: Int
)

data class AnalyticsPermissions(
  val dataCollection: DataCollection,
  val internalAnalysis: InternalAnalysis,
  val targetMarketing: TargetMarketing
)

data class EachEmailOptedIn(
  val deals: Deals,
  val survey: Survey
)

data class Timezone(
  val id: String,
  val name: String,
  val utcOffset: String,
  val utcOffsetSeconds: Int
)

data class DataCollection(
  val editable: Editable,
  val permitted: Boolean,
  val updatedAt: Int,
  val userConfirmed: Boolean
)

data class InternalAnalysis(
  val editable: Editable,
  val permitted: Boolean,
  val updatedAt: Int,
  val userConfirmed: Boolean
)

data class TargetMarketing(
  val editable: Editable,
  val permitted: Boolean,
  val updatedAt: Int,
  val userConfirmed: Boolean
)

data class Editable(
  val admin: Boolean,
  val self: Boolean
)

data class Deals(
  val optedIn: Boolean,
  val updatedAt: Int
)

data class Survey(
  val optedIn: Boolean,
  val updatedAt: Int
)

data class SplatnetAuthData(
  val body: String,
  val bullet_token: BulletToken,
  val cookies: String,
  val country: String,
  val created_at: Long,
  val expires_at: Long,
  val language: String,
  val queries: Queries,
  val url: String,
  val useragent: String,
  val version: String,
  val webserviceToken: WebserviceToken
)

data class BulletToken(
  val bulletToken: String,
  val is_noe_country: String,
  val lang: String
)

data class Queries(
  val `011e394c0e384d77a0701474c8c11a20`: List<Any>,
  val `0176a47218d830ee447e10af4a287b3f`: List<Any>,
  val `01fb9793ad92f91892ea713410173260`: List<Any>,
  val `02946c9d6dec617425ed41ee9a9bf467ea2ddfb85e0a36b09e4c3ea2e0b9ac5b`: List<Any>,
  val `0329c535a32f914fd44251be1f489e24`: List<Any>,
  val `0438ea6978ae8bd77c5d1250f4f84803`: List<Any>,
  val `0819c222d0b68fbcc7706f60b98e797da7d1fce637b45b3bdadca1ccdb692c86`: List<Any>,
  val `094a9b44ff21e8c409d6046fc1af9dfe`: Any,
  val `09eee118fa16415d6bc3846bc6e5d8e5`: List<Any>,
  val `0a62c0152f27c4218cf6c87523377521c2cff76a4ef0373f2da3300079bf0388`: List<Any>,
  val `0d90c7576f1916469b2ae69f64292c02`: List<Any>,
  val `0f8c33970a425683bb1bdecca50a0ca4fb3c3641c0b2a1237aedfde9c0cb2b8f`: List<Any>,
  val `10db4e349f3123c56df14e3adec2ee6f`: List<Any>,
  val `15035e6c4308b32d1a77e87398be5cd4`: List<Any>,
  val `18c7c465b18de5829347b7a7f1e571a1`: List<Any>,
  val `20f88b10d0b1d264fcb2163b0866de26bbf6f2b362f397a0258a75b7fa900943`: List<Any>,
  val `22e2fa8294168003c21b00c333c35384`: List<Any>,
  val `2376a887cccc4d31f40082fbc52d5231`: List<Any>,
  val `291295ad311b99a6288fc95a5c4cb2d2`: List<Any>,
  val `2aac81b2ec56fb2d15ce3d6a2b625772`: List<Any>,
  val `2acc36b477328ebb281fa91a07110e2d`: List<Any>,
  val `2b83817b6e88b202d25939fe04658d33`: List<Any>,
  val `2fb1b3fa2d40c9b5953ea1ae263e54c1`: List<Any>,
  val `31ff008ea218ffbe11d958a52c6f959f`: List<Any>,
  val `32383462b6412d446ae22a85a9b544eb`: List<Any>,
  val `34aedc79f96b8613501bba465295f779`: List<Any>,
  val `379f0d9b78b531be53044bcac031b34b`: List<Any>,
  val `3ab25d7f475cb3d5daf16f835a23411b`: List<Any>,
  val `3ba5572efce5bebbd859fc2d269d223c`: List<Any>,
  val `3baef04b095ad8975ea679d722bc17de`: List<Any>,
  val `3bd200163e63bfff42ab60a244cac042`: List<Any>,
  val `3cfc123fe1add3c924518c1550b2936c`: List<Any>,
  val `42262d241291d7324649e21413b29da88c0314387d8fdf5f6637a2d9d29954ae`: List<Any>,
  val `44c76790b68ca0f3da87f2a3452de986`: List<Any>,
  val `4869de13d0d209032b203608cb598aef`: List<Any>,
  val `49dd00428fb8e9b4dde62f585c8de1e0`: List<Any>,
  val `4c95233c8d55e7c8cc23aae06109a2e8`: List<Any>,
  val `4e357d607d98fa3b0f919f3aa0061af717c55c16017e31040647159bdb14601b`: List<Any>,
  val `4e83edd3d0964716c6ab27b9d6acf17f`: List<Any>,
  val `4e8b381ae6f9620443627f4eac3a2210`: List<Any>,
  val `4f9ae2b8f1d209a5f20302111b28f975`: List<Any>,
  val `50119df695739cd30f96dfe2f4d8bf8c`: List<Any>,
  val `50be9b694c7c6b99b7a383e494ec5258`: List<Any>,
  val `5149402597bd2531b4eea04692d8bfd5`: List<Any>,
  val `53ee6b6e2acc3859bf42454266d671fc`: List<Any>,
  val `53fb0ad32c13dd9a6e617b1158cc2d41`: List<Any>,
  val `563536def9d127eb5c66eef94f9f3e10e5af00b0be6b8faa1692ae259e023fb3`: List<Any>,
  val `5650c7abd4e377e74f95e30031864208`: List<Any>,
  val `573037eebfd48e676f2cfb78aa617138c4c789294f4f48e8bd4114bb5dc980c6`: List<Any>,
  val `5a199948d059985bd758cc0175131f4a`: List<Any>,
  val `5a469004feb402a1d44a10820b647def2d4eb320436f6add4431194a34d0b497`: List<Any>,
  val `5b563e5fb86ff7e537cc1ed86485049a41a710ca79af9c38113d41dda1d54643`: List<Any>,
  val `5c04e0793cca792c9724d4859a074964`: List<Any>,
  val `5d0d1b45ebf4e324d0dae017d9df06d2`: List<Any>,
  val `5f279779e7081f2d14ae1ddca0db2b6e`: List<Any>,
  val `5f8f333770ed3c43e21b0121f3a86716`: List<Any>,
  val `60a6592c6ee8e47245020ae0d314d378`: List<Any>,
  val `614b549a8be362c48c0dce773a95e9524a9338cfa9a7f47bf77a72a576f34122`: List<Any>,
  val `62383a0595fab69bf49a2a6877bc47acc081bfa065cb2eae28aa881980bb30b2`: List<Any>,
  val `636c7f8180469847bbfe005afb589ee041bc8ca653c2a26d07987e582179fcad`: List<Any>,
  val `63a60eea7926b0f2600cfb64d8bf3b6736afc1e1040beabd5dfa40fbfdcb92d8`: List<Any>,
  val `65252c7bbca148daf34de9a884e651bf9a5c1880a23f3d1e175a33f146b9f6dc`: List<Any>,
  val `67921048c4af8e2b201a12f13ad0ddae`: List<Any>,
  val `6796e3cd5dc3ebd51864dc709d899fc5`: List<Any>,
  val `68f99b7b02537bcb881db07e4e67f8dd`: List<Any>,
  val `6961f618fcef440c81509b205465eeec`: List<Any>,
  val `6b74405ca9b43ee77eb8c327c3c1a317`: List<Any>,
  val `6b8db227bbe479401875e509a95c3183931e708ec222a824f8d4157cebea4584`: List<Any>,
  val `6de3895bd90b5fa5220b5e9355981e16`: List<Any>,
  val `6dfce83d02761395758ae21454cb46924e81c22c3f151f91330b0602278a060e`: List<Any>,
  val `6eb1b255b2cf04c08041567148c883ad`: List<Any>,
  val `7161210aad0793e58e76f20e0443855e`: List<Any>,
  val `73b9837d0e4dd29bfa2f1a7d7ee0814a`: List<Any>,
  val `73bd677ed986ad2cb7004ceabfff4d38`: List<Any>,
  val `7d7194a98cb7b0b235f15f98a622fab4945992fd268101e24443db82569dd25d`: Any,
  val `7dcc64ea27a08e70919893a0d3f70871`: List<Any>,
  val `7e950e4f69a5f50013bba8a8fb6a3807`: List<Any>,
  val `8083b0c7f34a4bd0ef4a06ff86fc3e18`: List<Any>,
  val `81d9a6849467d2aa6b1603ebcedbddbe`: List<Any>,
  val `824a1e22c4ad4eece7ad94a9a0343ecd76784be4f77d8f6f563c165afc8cf602`: List<Any>,
  val `875a827a6e460c3cd6b1921e6a0872d8b95a1fce6d52af79df67734c5cc8b527`: List<Any>,
  val `87bff2b854168b496c2da8c0e7f3e5bc`: List<Any>,
  val `87ed3300bdecdb51090398d43ee0957e69b7bd1370ac38d03f6c7cb160b4586a`: Any,
  val `8892ce4157248506f51735e2c9eb300c6c980c67ff8c317b927b05e8d35852d9`: Any,
  val `89bc61012dcf170d9253f406ebebee67`: List<Any>,
  val `8a079214500148bf88a8fce1d7209b90`: List<Any>,
  val `8d3c5bb2e82d6eb32a37eefb0e1f8f69`: List<Any>,
  val `8e5ae78b194264a6c230e262d069bd28`: List<Any>,
  val `8e904b52b5080b6f4b4448a50762362c`: List<Any>,
  val `91b917becd2fa415890f5b47e15ffb15`: List<Any>,
  val `92b56403c0d9b1e63566ec98fef52eb3`: List<Any>,
  val `92f51ed1ab462bbf1ab64cad49d36f79`: List<Any>,
  val `93d0a1ccf461da6d23faea6340806f1b6b563f1c375b4a6a0ad35bc5f759f4b4`: List<Any>,
  val `940418e7b67b69420b7af50bdd292639e46fa8240ae57520a9cf7eed05a10760`: List<Any>,
  val `94711fc9f95dd78fc640909f02d09215`: List<Any>,
  val `951cab295eafdbeccfc2e718d7a98646`: List<Any>,
  val `96c3a7fd484b8d3be08e0a3c99eb2a3d`: List<Any>,
  val `9744fcf676441873c7c8a51285b6aa4d`: List<Any>,
  val `974fad8a1275b415c3386aa212b07eddc3f6582686e4fef286ec4043cdf17135`: Any,
  val `991bace9e8c52d63084cd1570a97a5b4`: List<Any>,
  val `994cf141e55213e6923426caf37a1934`: List<Any>,
  val `9b6b90568f990b2a14f04c25dd6eb53b35cc12ac815db85ececfccee64215edd`: List<Any>,
  val `9ee0099fbe3d8db2a838a75cf42856dd`: List<Any>,
  val a2c742c840718f37488e0394cd6e1e08: List<Any>,
  val a43dd44899a09013bcfd29b4b13314ff: List<Any>,
  val a5331ed228dbf2e904168efe166964e2be2b00460c578eee49fc0bc58b4b899c: List<Any>,
  val a5d80de05d1d4bfce67a1fb0801495d8bc6bba6fd780341cb90ddfeb1249c986: List<Any>,
  val a6782a0c692e8076656f9b4ab613fd82: List<Any>,
  val aa2c979ad21a1100170ddf6afea3e2db: List<Any>,
  val b13fb72c956c685ab4036a0369c67e98908c32aac2b425e0a105876567be3fab: List<Any>,
  val b23468857c049c2f0684797e45fabac1: List<Any>,
  val b2f05c682ed2aeb669a86a3265ceb713: List<Any>,
  val b79b7a101a243912754f72437e2ad7e5: List<Any>,
  val b96e7d0cbd9bc153f1e8860e6d143955b48664cec78cb8d11416c16eda6c2c35: List<Any>,
  val bb809066282e7d659d3b9e9d4e46b43b: List<Any>,
  val bc71fc0264f3f72256724b069f7a4097: List<Any>,
  val bc8a3d48e91d5d695ef52d52ae466920670d4f4381cb288cd570dc8160250457: List<Any>,
  val bdb796803793ada1ee2ea28e2034a31f5c231448e80f5c992e94b021807f40f8: List<Any>,
  val be2eb9e9b8dd680519eb59cc46c1a32b: List<Any>,
  val c0429fd738d829445e994d3370999764: List<Any>,
  val c0cd04d2f0b00444853bae0d7e7f1ac534dfd7ff593c738ab9ba4456b1e85f8a: List<Any>,
  val c8660a636e73dcbf55c12932bc301b1c9db2aa9a78939ff61bf77a0ea8ff0a88: Any,
  val c8d9828642f6eac6894876026d3db450: List<Any>,
  val cc38f388c51f9930bd7cca966893f1b4: List<Any>,
  val cd10e63e08dc83769052cfc28372a7ba: List<Any>,
  val cdf4ffe56864817014e59c569ec8630f: List<Any>,
  val d02ab22c9dccc440076055c8baa0fa7a: List<Any>,
  val d1841381ec4972f1bfc4742d162de0b3: List<Any>,
  val d1f062c14f74f758658b2703a5799002: List<Any>,
  val d29cd0c2b5e6bac90dd5b817914832f8: List<Any>,
  val d3188df2fd4436870936b109675e2849: List<Any>,
  val d46f88c2ea5c4daeb5fe9d5813d07a99: List<Any>,
  val d49fb6adffe15e3e43ca1167397debfc580eede3ad2232d7e32062bc5487e7eb: List<Any>,
  val d5e4924c05891208466fcba260d682e7: List<Any>,
  val d5fc5dd3a144139e89815b9e3af6499f58e5fc5185876840dd6edadb0ca214b4: List<Any>,
  val d62ec65b297968b659103d8dc95d014d: List<Any>,
  val d7657ef18434fbb5685381a9104ad7da24d090c5671e3bfd0c0e586542ed318e: List<Any>,
  val d771444f2584d938db8d10055599011d: List<Any>,
  val d9246baf077b2a29b5f7aac321810a77: List<Any>,
  val d935d9e9ba7a5b6b5d6ece7f253304fc: List<Any>,
  val d96057b8f46e5f7f213a35c8ea2b8fdc: List<Any>,
  val d997d8e3875d50d3a1dc7e8a756e9e07: List<Any>,
  val daffd9621680664dbf19d27e87484ac7: List<Any>,
  val dc5c1890cec78094d919e71621e9b4bc1ee06cfa99812dcacb401b8116a1ccad: List<Any>,
  val dd7f147589cf3bb45a08f53c3477056c: List<Any>,
  val e39d7ce9875a9d6940b4b449ed5b358b: List<Any>,
  val e7414c7a64bf80bb50ce21d5ccfde772: List<Any>,
  val e7bbaf1fa255305d607351da434b2d0f: List<Any>,
  val e9af725879a454fd3d5a191862ec3a544f552ae2d9bff6de6b212ac2676e8e14: Any,
  val e9cbaa835977b6c6de77ca7a4be15b24: List<Any>,
  val eb69df6f2a2f13ab207eedc568f0f8b6: List<Any>,
  val eb947416660e0a7520549f6b9a8ffcd7: List<Any>,
  val ebd88adbba13f09100f9326b1ec4c348: List<Any>,
  val ec7174376203f9901713e116075c5ecd: List<Any>,
  val eef75ef7ce1964dfe9006bf5facde61e: List<Any>,
  val f08a932d533845dde86e674e03bbb7d3: List<Any>,
  val f09666535a18dfe2a0953018a8e7138204fb9d007cc32bd2c85f3e0f7c1cc6ba: List<Any>,
  val f09da9d24d888797fdfb2f060dbdf4ed: List<Any>,
  val f0a8ebc384cf5fbac01e8085fbd7c898: List<Any>,
  val f488fccdad37b9e19aed50a8d6e83a24: List<Any>,
  val f76dd61e08f4ce1d5d5b17762a243fec: List<Any>,
  val f893e1ddcfb8a4fd645fd75ced173f18b2750e5cfba41d2669b9814f6ceaec46: List<Any>,
  val f8ae00773cc412a50dd41a6d9a159ddd: List<Any>,
  val fb960404299958248b3c0a2fbb444c35: List<Any>,
  val fbee1a882371d4e3becec345636d7d1c: List<Any>,
  val ff12098bad4989a813201b00ff22ac4e: List<Any>
)

data class WebserviceToken(
  val accessToken: String,
  val expiresIn: Int
)

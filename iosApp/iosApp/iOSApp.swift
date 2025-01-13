import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        AppModuleKt.doInitKoin { Koin_coreKoinApplication in
            
        }
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

![](https://mclegoman.com/images/8/8a/Luminance_development_logo.png)  
## Luminance 1.0.0-alpha.1 for 1.20.5-rc2  
  

## Changelog  
### Config Version 1  
- Added integer config option `alpha_level`.
  - This value is clamped between 0 and 100.
### Features  
- Added **Dynamic Shader Uniforms**.
  - You can add custom dynamic shader uniforms using:
    - `ShaderRenderEvents.ShaderUniform.registerFloat("modid", "example", Uniforms::getExample);`  
    - `ShaderRenderEvents.ShaderUniform.registerFloats("modid", "example", Uniforms::getExample);`  
    - `ShaderRenderEvents.ShaderUniform.registerVector3f("modid", "example", Uniforms::getExample);`  
    - In this example, your dynamic uniform name will be `modid_example`.  
  - Luminance also comes with some already registered.
    - `viewDistance`
    - `fov`
    - `fps`
    - `time`
    - `eyePosition`
    - `position`
    - `pitch`
    - `yaw`
    - `currentHealth`
    - `maxHealth`
    - `currentAbsorption`
    - `maxAbsorption`
    - `currentHurtTime`
    - `maxHurtTime`
    - `currentAir`
    - `maxAir`
    - `isSprinting`
    - `isSwimming`
    - `isSneaking`
    - `isCrawling`
    - `isInvisible`
    - `isWithered`
    - `isPoisoned`
    - `isBurning`
    - `isOnGround`
    - `isOnLadder`
    - `isRiding`
    - `hasPassengers`
    - `biomeTemperature`
    - `alpha`
    - `perspective`
    - `viewDistanceSmooth`
    - `fovSmooth`
    - `timeSmooth`
    - `eyePositionSmooth`
    - `positionSmooth`
    - `pitchSmooth`
    - `yawSmooth`
    - `currentHealthSmooth`
    - `maxHealthSmooth`
    - `currentAbsorptionSmooth`
    - `maxAbsorptionSmooth`
    - `currentHurtTimeSmooth`
    - `maxHurtTimeSmooth`
    - `currentAirSmooth`
    - `maxAirSmooth`
    - `isSprintingSmooth`
    - `isSwimmingSmooth`
    - `isSneakingSmooth`
    - `isCrawlingSmooth`
    - `isInvisibleSmooth`
    - `isWitheredSmooth`
    - `isPoisonedSmooth`
    - `isBurningSmooth`
    - `isOnGroundSmooth`
    - `isOnLadderSmooth`
    - `isRidingSmooth`
    - `hasPassengersSmooth`
    - `biomeTemperatureSmooth`
    - `alphaSmooth`
  - **This list of included dynamic shader uniforms will change over time, more will be added in future updates.**
    - If you are a shader developer and need something custom, or just have a suggestion for a new dynamic shader uniform, add an issue to the [Issues](https://github.com/MCLegoMan/Perspective/issues) page with what you need, and it'll get added.
### Resource Packs  
#### Perspective: Default  
- Added **Super Secret Settings** Resource Pack.  
  - This resource pack contains shaders that have been used in previous minecraft versions.
  - _Requires [Perspective](https://modrinth.com/mod/mclegoman) or [Souper Secret Settings](https://modrinth.com/mod/souper-secret-settings)._

## Development Build  
Please help us improve by submitting [bug reports](https://github.com/MCLegoMan/Perspective/issues) if you encounter any issues.  

### Want to support my work?  
If you'd like to donate, visit [BuyMeACoffee](https://www.buymeacoffee.com/mclegoman).  
Your support is appreciated, please be aware that donations are non-refundable.  
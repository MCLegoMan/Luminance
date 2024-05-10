## Changelog  
### Features  
- Added Message Overlay.  
- Updated Events.  
  - `OnShaderDataReset`  
    - Runnables registered here will run when the shader registry is reset.  
      - It will most commonly happen when reloading resources.  
  - `OnShaderDataRegistered`  
    - Runnables.ShaderData registered here will run when a shader is registered in the dataloader.  
  - `OnShaderDataRemoved`  
    - Runnables.ShaderData registered here will run when a shader is removed from the dataloader.  
      - It will most commonly happen if a resource pack sets the shader's `enabled` variable to false, after the shader has already been registered.  
  - `AfterShaderDataRegistered`  
    - Runnables registered here will run after the shader dataloader has finished loading.  
  - `BeforeWorldRender`  
    - Runnables registered here will run before the world is rendered.  
  - `AfterWorldBorder`  
    - Runnables registered here will run after the world border is rendered.  
  - `AfterWorldRender`  
    - Runnables registered here will run after the world is rendered.  
  - `AfterGameRender`  
    - Runnables registered here will run after everything is rendered.  
  - `BeforeShaderRender`  
    - Runnables registered here will run before a shader is rendered.  
  - `AfterShaderRender`  
    - Runnables registered here will run after a shader is rendered.  
  - `ShaderUniform`  
    - You can now modify/remove uniforms.  
      - If you want to update a uniform, make the callable call a variable.  
      - You should only modify the uniform if you are changing what the callable calls.  
  - `ShaderRender`  
    - Shaders registered here will be rendered by Luminance.  
      - When registering a shader, you will include a `new Couple<>(modId, shaders)` id, this allows mods to have multiple shaders rendered and allows you to modify them.  
      - When registering a shader, you will also include a list of shaders.  
        - `new Couple<String, Shader>("id", new Shader(id, renderType))`
          - The shader is stored in a couple and consists of a String and the shader itself.  
            - The string can be anything, but there can only be one shader with that string.  
              - It can be used to locate a specific shader to either modify or remove.  
            - The shader consists of an Identifier and a Shader.RenderType.  
              - The Identifier should lead to your `/shaders/post/x.json` file.  
              - The RenderType can be set to `Shader.RenderType.GAME` or `Shader.RenderType.WORLD`.  
                - `GAME` renders the shader over everything including menus.  
                  - Shaders with `disable_game_rendertype` set to true will render in `WORLD` instead.  
                - `WORLD` renders only in-game behind your GUI.  
- Added Shader Namespace Identifier Fix.  
- Added Shader Texture Namespace Identifier Fix.  
- Added Shader Dataloader.  
  - Shaders registered here will be added to ShaderDataloader.registry.  
  - The layout is based on Perspective's shader dataloader, but shaders made with this layout will **NOT** get registered by Perspective (yet, it'll be updated to use this layout instead in a future update!)
  - Dataloader Example  
    - This example will register the `perspective:silhouette` shader, will always render in the WORLD rendertype, and can be translated.  
    - The custom field can contain objects that can be read by third party mods - e.g. perspective.  
```
{
  "namespace": "perspective",
  "name": "silhouette",
  "enabled": true,
  "translatable": true,
  "disable_game_rendertype": true,
  "custom": {
    "perspective": {
      "entity_links": [
        "entity.minecraft.warden"
      ]
    },
    "souper_secret_settings": {
      "disable_soup": false,
      "entity_links": [
        "entity.minecraft.warden"
      ]
    }
  }
}
```
  - If you're updating a perspective shader, you will need to update the following:  
    - `shader` has been renamed to `name`.  
    - `disable_screen_mode` has been renamed to `disable_game_rendertype`.  
    - `entity_links` is now contained within the `perspective` `custom` object.  
- Added ModMenu Icon Override.  
  - You can override the icon ModMenu displays for your mod by using the following code on mod initialization:  
    - `CompatHelper.addOverrideModMenuIcon(new Couple<>("modId", "type"), "assets/modId/alt_icon.png", () -> {return true;});`  
- Added ModMenu Luminance Badge.  
  - You can add the Luminance ModMenu Badge to your mod by using the following code on mod initialization.  
    - `CompatHelperaddLuminanceModMenuBadge("modId");`  
### Config Version 2  
- Added `show_alpha_level_overlay` boolean config option.  
  - When set to true, changing the alpha level using the keybinding will display the percentage on screen.
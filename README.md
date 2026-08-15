# TheGame — AI Text Adventure Game

[简体中文](README.zh-CN.md) | English

TheGame is a text adventure game where an AI acts as the game master. Describe the world you want in a single sentence, and the game generates a complete world view, protagonist, NPCs, and rules for you — then keeps telling the story as you play.

## Features

- 🌍 **Create a world in one sentence** — Describe the game world you want, and the AI fills in the world name, time, location, social structure, history and legends, protagonist background, and initial NPCs.
- 🧙 **Fully customizable protagonist** — The protagonist's name, background, and attribute system are generated to fit the genre of the world, and keep growing and changing throughout the game.
- 👥 **Lifelike NPCs** — Every NPC has their own personality, background, stance, and perception of the protagonist. They remember what happened and are changed by the story.
- 🎭 **AI-driven storytelling** — The AI plays all NPCs and the narrator. Every response includes full scene descriptions, character actions, dialogue, and plot progression.
- 🧭 **Action choices** — Each turn offers 3–4 suggested actions, or you can type any action you like.
- 🎼 **Dynamic soundtrack** — Background music switches automatically with the mood of the story: battle, danger, romance, seasons, and victory each have their own tracks.
- 🖼️ **Scene illustration** — Generate an illustration of the current scene at any time, in either realistic or anime style.

## Core Gameplay Loop

```
Create a world → Type or choose an action → AI advances the story →
World and character states update → Choose the next action
```

Every turn is a complete narrative: the AI describes the light, sound, and smells of the environment, shows the expressions and actions of NPCs, moves events forward, and then suggests what you can do next.

## World Generation

When creating a game, simply describe an idea, for example:

- "A harbor town shrouded in a fog that never lifts"
- "I am an amnesiac star mercenary waking up on an abandoned colony ship"
- "In the glorious Tang Dynasty, monsters lurk beneath the surface"

The AI then generates:

- A game name and a world name
- World description, history, and legends
- A genre-appropriate time setting (e.g. "Stardate 3042, July")
- The starting location and social structure
- A protagonist background tightly tied to the world
- 5–8 initial NPCs, each with personality, background, and a relationship to the protagonist

## Attribute System

Every world has its own attribute system instead of a generic "HP + coins". Attributes are designed around the genre and genuinely affect the story:

- **Numeric** — quantifiable with a range, such as health, sanity, inner power, or radiation dose.
- **Boolean** — yes/no states, such as wanted or poisoned.
- **Enum** — a fixed set of values, such as faction, identity, or reputation level.
- **Text** — free-form description, such as occupation, title, or secret identity.
- **Table** — a structured list, such as skills, equipment, quests, or relationship networks.

Attributes interact and create trade-offs. "Forbidden Knowledge" may increase your power while draining your Sanity; high Reputation opens some doors but may also attract enemies. As the story develops, the AI can unlock new attributes or make old ones meaningless.

## NPC System

NPCs are not just dialogue vending machines:

- Every NPC has a personality, appearance, background, current mood, and perception of the protagonist.
- NPCs remember important experiences; their backgrounds and appearances update with the story.
- NPCs can be introduced, die, or leave; important characters are preserved.
- Every important NPC has a hidden **secret agenda** the player cannot see. They have their own plans and may lie, test, betray, or ask for help — you can only sense it through details in what they say and do.

## World Rule Details

Long-term facts revealed during the game are stored as "World Rule Details" — for example the layout of a city, the rules of an organization, or how a certain power works.

- Related information is merged into existing entries instead of creating duplicates.
- Once written, a rule is not deleted casually.
- These details continue to influence later events, keeping the world consistent.

## Director Instructions

Behind the visible story, a "Director" secretly arranges the drama:

- Each turn may contain a hidden dramatic instruction, such as making an NPC lie, introducing time pressure, planting foreshadowing, or creating a dilemma.
- The instruction is completely invisible to the player and is only woven into the outcome of the story.
- Foreshadowing is remembered and paid off a few turns later. NPCs act on their own, so the world does not revolve around the protagonist alone.
- Director instructions take priority over player requests — this is why the plot does not always go the way you expect.

## Time Progression

Game time does not stand still:

- The first turn establishes the starting time of the story.
- Day and night, seasons, travel, and long conversations move time forward in a way that fits the world.
- Time expressions keep the flavor of the world, such as "Year 1247 of the Third Era, late autumn, at dusk".

## Story Summaries

- About every 30 turns, the AI automatically generates a complete, self-contained progress summary.
- The summary includes key events, scene changes, and pending matters.
- Later turns continue from the summary, so even very long adventures do not lose the main plot.

## Rewind and Regenerate

- The game automatically saves progress after every turn.
- You can "regenerate" from any previous turn: return to that point and let the AI write a different development using the same world, characters, and rules.
- The overwritten future is reset together, keeping the new story line coherent from the start.

## License

See the [LICENSE](LICENSE) file.

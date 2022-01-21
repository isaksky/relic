# Change Log

## 0.1.3

- [#47](https://github.com/wotbrew/relic/issues/47) db without meta is acceptable to queries
- fixed nil behaviour on multi-expr count-distinct
- set-concat returns empty-set if no non-nil values

## 0.1.2

### Fixed

- fixed glitches with materialized sorted-group deletes
- `(index)` now returns nil for non-index operators (keep it secret, keep it safe.)

## 0.1.1 

### Fixed

- [#45](https://github.com/wotbrew/relic/issues/45) join expr that return nil are not used in index lookups

## 0.1.0 

Initial release
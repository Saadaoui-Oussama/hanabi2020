package model;

public enum FireworkColor {
	white {
		@Override
		public String toString() {
			return "blanche";
		}
	}, blue {
		@Override
		public String toString() {
			return "bleue";
		}
	}, yellow {
		@Override
		public String toString() {
			return "jaune";
		}
	}, red {
		@Override
		public String toString() {
			return "rouge";
		}
	}, green {
		@Override
		public String toString() {
			return "verte";
		}
	};
}

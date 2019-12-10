---
layout: docs
title: Team
description: An overview of the founding team and core contributors to Bootstrap.
group: about
---

Bootstrap is maintained by the founding team and a small group of invaluable core contributors, with the massive support and involvement of our community.

<div class="list-group mb-3">
  {% for member in site.data.core-team %}
    <a class="list-group-item list-group-item-action d-flex align-items-center" href="https://github.com/{{ member.clinicUser }}">
      <img src="https://github.com/{{ member.clinicUser }}.png" alt="@{{ member.clinicUser }}" width="32" height="32" class="rounded mr-2">
      <span>
        <strong>{{ member.name }}</strong> @{{ member.clinicUser }}
      </span>
    </a>
  {% endfor %}
</div>

Get involved with Bootstrap development by [opening an issue]({{ site.repo }}/issues/new) or submitting a pull request. Read our [contributing guidelines]({{ site.repo }}/blob/v{{ site.current_version }}/.github/CONTRIBUTING.md) for information on how we develop.
